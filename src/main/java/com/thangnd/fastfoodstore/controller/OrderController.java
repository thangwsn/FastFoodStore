package com.thangnd.fastfoodstore.controller;

import com.paypal.api.payments.Links;
import com.paypal.base.rest.PayPalRESTException;
import com.thangnd.fastfoodstore.entity.order.*;
import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.service.CartService;
import com.thangnd.fastfoodstore.service.OrderService;
import com.thangnd.fastfoodstore.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private PayPalService payPalService;

    @GetMapping
    public String showAllOrderForCustomer(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Customer")) {
            List<Order> orderList = orderService.getAllOrderByCustomerID((Customer) user);
            if (orderList != null) {
                model.addAttribute("orderList", orderList);
            } else {
                model.addAttribute("message", "You don't have any orders yet.");
            }
            return "customer/order";
        }
        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public String showOrderDetail(@PathVariable("id") String orderID, Model model, HttpSession session) {
        Order order = orderService.getOrderByID(orderID);
        model.addAttribute("order", order);
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Customer")) {
            return "customer/order-detail";
        }
        if (user != null && user.getRole().name().equals("Staff")) {
            return "staff/order-detail";
        }
        return "redirect:/login";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("checkoutLine") Optional<String> checkoutLinesStr,
                           @ModelAttribute("cart") Cart cart,
                           Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Customer")) {
            cartService.updateCart(cart);
            if (checkoutLinesStr.isPresent()) {
                Order order = new Order();
                order.setID(UUID.randomUUID().toString());
                String[] checkoutLinesArr = checkoutLinesStr.get().split(",");
                List<OrderLine> orderLineList = new ArrayList<>();
                for (int i = 0; i < checkoutLinesArr.length; i++) {
                    CartLine cartLine = cartService.getCartLineByID(checkoutLinesArr[i]);
                    // order line id same cart line id
                    if (cartLine != null) {
                        orderLineList.add(new OrderLine(cartLine.getID(), order, cartLine.getFood(), cartLine.getQuantity(), cartLine.getPrice()));
                    }
                }
                order.setOrderLineList(orderLineList); //list
                double total = orderLineList.stream().mapToDouble(orderLine -> orderLine.getQuantity() * orderLine.getPrice()).sum();
                order.setTotal(total); //set total
                order.setPayment(new Payment()); // payment
                Shipment shipment = new Shipment();
                shipment.setFullName(user.getFullName());
                shipment.setAddress(user.getAddress());
                shipment.setMobile(user.getMobile());
                order.setShipment(shipment); //shipment
                model.addAttribute("order", order);
                return "customer/checkout";
            } else {
                return "redirect:/cart";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/placeorder")
    public String placeOrder(@Valid @ModelAttribute(name = "order") Order order,
                             BindingResult result, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Customer")) {
            if (result.hasErrors()) {
                model.addAttribute("order", order);
                return "customer/checkout";
            }
            orderService.placeOrder(order, user);
            if (order.getPayment().getMethod().equals("Check")) {
                try {
                    com.paypal.api.payments.Payment payment =
                            payPalService.createPayment(order.getPayment().getAmount(),
                                    "USD", "PayPal",
                                    "Sale", "Checkout for food",
                                    "http://localhost:8080/order/paymentFail/" + order.getID(),
                                    "http://localhost:8080/order/paymentSuccess/" + order.getID()
                            );
                    for (Links links : payment.getLinks()) {
                        if (links.getRel().equals("approval_url")) {
                            return "redirect:" + links.getHref();
                        }
                    }
                } catch (PayPalRESTException e) {
                    e.printStackTrace();
                }
            }
            return "redirect:/order/" + order.getID(); // redirect order-detail
        }
        return "redirect:/login";
    }

    @GetMapping("/paymentFail/{orderID}")
    public String paymentFail(@PathVariable("orderID") String orderID) {
        return "redirect:/order/" + orderID; // redirect order-detail
    }

    @GetMapping("/paymentSuccess/{orderID}")
    public String paymentSuccess(@PathVariable("orderID") String orderID) {
        orderService.setPaidForPayment(orderID);
        return "redirect:/order/" + orderID; // redirect order-detail
    }

    @PostMapping("/update")
    public String updateOrderStatus(@RequestParam("orderID") String orderID,
                                    @RequestParam("status") String orderStatus,
                                    HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        } else {
            if (user.getRole().name().equals("Customer")) {
                orderService.updateOrderForCustomer(orderID, orderStatus);
                return "redirect:/order";
            } else {
                orderService.updateOrderForStaff(orderID, orderStatus);
                return "redirect:/dashboard/order";
            }
        }
    }
}

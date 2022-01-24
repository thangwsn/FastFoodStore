package com.thangnd.fastfoodstore.controller;

import com.thangnd.fastfoodstore.entity.order.Order;
import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.service.CustomerService;
import com.thangnd.fastfoodstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public String showCustomerDetail(@PathVariable("id") String id, Model model,
                                     HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Customer customer = customerService.getCustomerByID(id);
            List<Order> orderList = orderService.getAllOrderByCustomerID(customer);
            double spending = customerService.getSpendingByCustomer(customer);
            model.addAttribute("customer", customer);
            model.addAttribute("orderList", orderList);
            model.addAttribute("spending", spending);
            if (user.getRole().name().equals("Staff")) {
                return "staff/customer-detail";
            } else {
                return "/customer-detail";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/edit")
    public String editLevel(@RequestParam("customerID")Optional<String> customerID,
                            @RequestParam("level") Optional<String> level,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (customerID.isPresent() && level.isPresent()) {
                customerService.editLevel(customerID.get(), level.get());
            }
            return "redirect:/dashboard/customer";
        }
        return "redirect:/login";
    }

}

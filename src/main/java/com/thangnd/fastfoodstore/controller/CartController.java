package com.thangnd.fastfoodstore.controller;

import com.thangnd.fastfoodstore.entity.order.Cart;
import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Customer")) {
            Cart cart = cartService.getCartByCustomerID(user.getID());
            model.addAttribute("cart", cart);
            return "customer/cart";
        }
        return "redirect:/login";
    }

    @GetMapping("/add")
    public String addToCart(@RequestParam("id") int foodID,
                            @RequestParam("quantity") int quantity,
                            HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Customer")) {
            cartService.addToCart(foodID, quantity, user);
            return "redirect:/cart";
        }
        return "redirect:/login";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable("id") String cartLineID,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Customer")) {
            cartService.removeFromCart(cartLineID);
            redirectAttributes.addFlashAttribute("message", "Remove Successfully!");
            return "redirect:/cart";
        }
        return "redirect:/login";
    }

    @PostMapping("/update")
    public String updateCart(@ModelAttribute Cart cart, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Customer")) {
            cartService.updateCart(cart);
            return "redirect:/cart";
        }
        return "redirect:/login";
    }
}

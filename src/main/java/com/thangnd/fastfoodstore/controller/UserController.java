package com.thangnd.fastfoodstore.controller;

import com.thangnd.fastfoodstore.entity.food.ComboFood;
import com.thangnd.fastfoodstore.entity.food.SingleFood;
import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.entity.user.Role;
import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.service.CustomerService;
import com.thangnd.fastfoodstore.service.FoodService;
import com.thangnd.fastfoodstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;

    @GetMapping
    public String loadHome(Model model) {
        List<SingleFood> singleFoodList = foodService.getBestSellerOfSingleFood();
        List<ComboFood> comboFoodList = foodService.getBestSellerOfComboFood();
        model.addAttribute("singleFoodList", singleFoodList);
        model.addAttribute("comboFoodList", comboFoodList);
        return "index";
    }

    @GetMapping("/register")
    public String showCustomerRegistration(@ModelAttribute Customer customer, Model model) {
        model.addAttribute("customer", customer);
        return "registration-form";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute Customer customer, BindingResult result,
                           @RequestParam("confirmPassword") String confirmPassword,
                           Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("customer", customer);
            return "registration-form";
        } else {
            if (!confirmPassword.equals(customer.getPassword())) {
                model.addAttribute("customer", customer);
                model.addAttribute("message", "Confirm password is incorrect!");
                return "registration-form";
            }
            if (customerService.emailIsExist(customer.getEmail())) {
                model.addAttribute("customer", customer);
                model.addAttribute("message", "Email already exists!");
                return "registration-form";
            }
            if (customerService.mobileIsExist(customer.getMobile())) {
                model.addAttribute("customer", customer);
                model.addAttribute("message", "Mobile already exists!");
                return "registration-form";
            }
            customerService.createCustomer(customer);
            redirectAttributes.addFlashAttribute("message", "Register successfully!");
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login-form";
    }

    @GetMapping("/redirect")
    public String authorRedirect(Principal principal, HttpSession session) {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
        User currentUser = userService.getUserByEmail(user.getUsername());
        // save Session
        session.setAttribute("user", currentUser);
        if (user.getAuthorities().contains( new SimpleGrantedAuthority(Role.Customer.name())) ) {
            return "redirect:/";
        }
        else if (user.getAuthorities().contains( new SimpleGrantedAuthority(Role.Staff.name())) ) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

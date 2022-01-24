package com.thangnd.fastfoodstore.controller;

import com.thangnd.fastfoodstore.entity.food.Category;
import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.service.FoodService;
import com.thangnd.fastfoodstore.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private FoodService foodService;

    @GetMapping
    public String showAllCategory(@ModelAttribute Category category,
                                  HttpSession session,
                                  Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            List<Category> categoryList = foodService.getAllCategory();
            categoryList = foodService.updateFoodQuantity(categoryList);
            model.addAttribute("category", category);
            model.addAttribute("categoryList", categoryList);
            return "staff/dashboard-category";
        }
        return "redirect:/login";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute Category category,
                              BindingResult result, Model model,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (result.hasErrors()) {
                model.addAttribute("category", category);
                model.addAttribute("showModal", "true");
                return "staff/category";
            } else {
                category.setFoodQuantity(0);
                if (foodService.saveCategory(category) != null) {
                    redirectAttributes.addFlashAttribute("message", "Add successfully!");
                    return "redirect:/category";
                } else {
                    model.addAttribute("category", category);
                    model.addAttribute("showModal", "true");
                    return "staff/category";
                }
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/edit")
    public String editCategory(@Valid @ModelAttribute Category category, BindingResult result,
                               @RequestParam("id") int categoryID,
                               @RequestParam("foodQuantity") int foodQuantity,
                               Model model, RedirectAttributes redirectAttributes,
                               HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (result.hasErrors()) {
                model.addAttribute("category", category);
                return "staff/category";
            } else {
                category.setID(categoryID);
                category.setFoodQuantity(foodQuantity);
                if (foodService.saveCategory(category) != null) {
                    redirectAttributes.addFlashAttribute("message", "Edit successfully!");
                    return "redirect:/category";
                } else {
                    model.addAttribute("category", category);
                    model.addAttribute("showModal", "true");
                    return "staff/category";
                }
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/remove/{id}")
    public String removeCategory(@PathVariable("id") int id, RedirectAttributes redirectAttributes,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (id != Utils.COMBO_TYPE && id != Utils.OTHER_TYPE) {
                foodService.removeCategory(id);
            }
            redirectAttributes.addFlashAttribute("message", "Delete Sucessfully");
            return "redirect:/category";
        }
        return "redirect:/login";
    }
}

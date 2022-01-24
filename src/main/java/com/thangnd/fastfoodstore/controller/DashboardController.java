package com.thangnd.fastfoodstore.controller;

import com.thangnd.fastfoodstore.entity.food.Category;
import com.thangnd.fastfoodstore.entity.food.Food;
import com.thangnd.fastfoodstore.entity.order.Order;
import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.model.CustomerStatistic;
import com.thangnd.fastfoodstore.model.FoodStatistic;
import com.thangnd.fastfoodstore.service.CustomerService;
import com.thangnd.fastfoodstore.service.FoodService;
import com.thangnd.fastfoodstore.service.OrderService;
import com.thangnd.fastfoodstore.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String showDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            long totalFood = foodService.getFoodTotal();
            long totalCustomer = customerService.getTotalCustomer();
            long totalOrder = orderService.getTotalOrder();
            double sales = orderService.getTotalSales();
            List<FoodStatistic> foodStatisticList = foodService.getSalesForFood();
            List<CustomerStatistic> customerStatisticList = customerService.getCustomerSpending();

            model.addAttribute("totalFood", totalFood);
            model.addAttribute("totalCustomer", totalCustomer);
            model.addAttribute("totalOrder", totalOrder);
            model.addAttribute("sales", sales);
            model.addAttribute("foodStatisticList", foodStatisticList);
            model.addAttribute("customerStatisticList", customerStatisticList);
            return "staff/dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/food")
    public String showFoodDashboard(@RequestParam("category") Optional<String> category,
                                    Model model, HttpSession session) {
        if (!category.isPresent()) {
            return paginatedFood(1, Optional.of("All"), Optional.of("ID"), Optional.of(Utils.ASC_SORT), model, session);
        } else {
            return paginatedFood(1, category, Optional.of("ID"), Optional.of(Utils.ASC_SORT), model, session);
        }

    }

    @GetMapping("/food/page/{pageNo}")
    public String paginatedFood(@PathVariable("pageNo") int pageNo,
                                @RequestParam("category") Optional<String> category,
                                @RequestParam("sortField") Optional<String> sortField,
                                @RequestParam("sortDirection") Optional<String> sortDirection,
                                Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (!category.isPresent() || category.get().equals("All")) {
                if (!sortField.isPresent()) {
                    sortField = Optional.of("ID");
                }
                if (!sortDirection.isPresent()) {
                    sortDirection = Optional.of(Utils.ASC_SORT);
                }
                Page<Food> page = foodService.findPaginatedFood(pageNo, Utils.PAGE_SIZE, sortField.get(), sortDirection.get());
                List<Food> foodList = page.getContent();
                model.addAttribute("foodList", foodList);
                model.addAttribute("currentPage", pageNo);
                model.addAttribute("totalPages", page.getTotalPages());
                model.addAttribute("totalFoods", page.getTotalElements());
                model.addAttribute("sortField", sortField.get());
                model.addAttribute("sortDirection", sortDirection.get());
                model.addAttribute("reverseSortDirection", sortDirection.get().equals(Utils.DESC_SORT) ? Utils.ASC_SORT : Utils.DESC_SORT);
                return "staff/dashboard-food";
            } else {
                if (!sortField.isPresent()) {
                    sortField = Optional.of("ID");
                }
                if (!sortDirection.isPresent()) {
                    sortDirection = Optional.of(Utils.ASC_SORT);
                }
                Category categoryObj = foodService.getCategoryByID(Integer.parseInt(category.get()));
                Page<Food> page = foodService.findPaginatedFoodByCategory(categoryObj, pageNo, Utils.PAGE_SIZE, sortField.get(), sortDirection.get());
                List<Food> foodList = page.getContent();
                model.addAttribute("foodList", foodList);
                model.addAttribute("category", categoryObj);
                model.addAttribute("currentPage", pageNo);
                model.addAttribute("totalPages", page.getTotalPages());
                model.addAttribute("totalFoods", page.getTotalElements());
                model.addAttribute("sortField", sortField.get());
                model.addAttribute("sortDirection", sortDirection.get());
                model.addAttribute("reverseSortDirection", sortDirection.get().equals(Utils.DESC_SORT) ? Utils.ASC_SORT : Utils.DESC_SORT);
                return "staff/dashboard-food-by-category";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/food/search")
    public String searchFood(@RequestParam("key") Optional<String> key,
                             Model model, HttpSession session) {
        return paginatedFoodSearch(1, key, Optional.of("ID"), Optional.of(Utils.ASC_SORT), model, session);

    }

    @GetMapping("/food/search/page/{pageNo}")
    public String paginatedFoodSearch(@PathVariable("pageNo") int pageNo,
                                      @RequestParam("key") Optional<String> key,
                                      @RequestParam("sortField") Optional<String> sortField,
                                      @RequestParam("sortDirection") Optional<String> sortDirection,
                                      Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (!sortField.isPresent()) {
                sortField = Optional.of("ID");
            }
            if (!sortDirection.isPresent()) {
                sortDirection = Optional.of(Utils.ASC_SORT);
            }
            Page<Food> page = foodService.findPaginatedFoodContainKeyName(key.get(), pageNo, Utils.PAGE_SIZE, sortField.get(), sortDirection.get());
            List<Food> foodList = page.getContent();
            model.addAttribute("foodList", foodList);
            model.addAttribute("key", key.get());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalFoods", page.getTotalElements());
            model.addAttribute("sortField", sortField.get());
            model.addAttribute("sortDirection", sortDirection.get());
            model.addAttribute("reverseSortDirection", sortDirection.get().equals(Utils.DESC_SORT) ? Utils.ASC_SORT : Utils.DESC_SORT);
            return "staff/dashboard-food-search-contain-name";
        }
        return "redirect:/login";
    }

    @GetMapping("/customer")
    public String showAllCustomer(Model model, HttpSession session) {
        return paginatedCustomer(1, Optional.of("fullName"), Optional.of(Utils.ASC_SORT), model, session);
    }

    @GetMapping("/customer/page/{pageNo}")
    public String paginatedCustomer(@PathVariable("pageNo") int pageNo,
                                    @RequestParam("sortField") Optional<String> sortField,
                                    @RequestParam("sortDirection") Optional<String> sortDirection,
                                    Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (!sortField.isPresent()) {
                sortField = Optional.of("fullName");
            }
            if (!sortDirection.isPresent()) {
                sortDirection = Optional.of(Utils.ASC_SORT);
            }
            Page<Customer> page = customerService.findPaginatedCustomer(pageNo, Utils.PAGE_SIZE, sortField.get(), sortDirection.get());
            List<Customer> customerList = page.getContent();
            model.addAttribute("customerList", customerList);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalCustomers", page.getTotalElements());
            model.addAttribute("sortField", sortField.get());
            model.addAttribute("sortDirection", sortDirection.get());
            model.addAttribute("reverseSortDirection", sortDirection.get().equals(Utils.DESC_SORT) ? Utils.ASC_SORT : Utils.DESC_SORT);
            return "staff/dashboard-customer";
        }
        return "redirect:/login";
    }

    @GetMapping("/order")
    public String showAllOrder(Model model, HttpSession session) {
        return paginatedOrder(1, Optional.of("createTime"), Optional.of(Utils.DESC_SORT), model, session);
    }

    @GetMapping("/order/page/{pageNo}")
    public String paginatedOrder(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("sortField") Optional<String> sortField,
                                 @RequestParam("sortDirection") Optional<String> sortDirection,
                                 Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (!sortField.isPresent()) {
                sortField = Optional.of("createTime");
            }
            if (!sortDirection.isPresent()) {
                sortDirection = Optional.of(Utils.DESC_SORT);
            }
            Page<Order> page = orderService.findPaginatedOrder(pageNo, Utils.PAGE_SIZE, sortField.get(), sortDirection.get());
            List<Order> orderList = page.getContent();
            model.addAttribute("orderList", orderList);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalOrders", page.getTotalElements());
            model.addAttribute("sortField", sortField.get());
            model.addAttribute("sortDirection", sortDirection.get());
            model.addAttribute("reverseSortDirection", sortDirection.get().equals(Utils.DESC_SORT) ? Utils.ASC_SORT : Utils.DESC_SORT);
            return "staff/dashboard-order";
        }
        return "redirect:/login";
    }

}

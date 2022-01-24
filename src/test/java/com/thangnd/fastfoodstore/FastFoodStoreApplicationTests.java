package com.thangnd.fastfoodstore;

import com.thangnd.fastfoodstore.entity.food.Category;
import com.thangnd.fastfoodstore.entity.food.Food;
import com.thangnd.fastfoodstore.entity.order.Cart;
import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.repository.*;
import com.thangnd.fastfoodstore.service.FoodService;
import com.thangnd.fastfoodstore.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class FastFoodStoreApplicationTests {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    SingleFoodRepo singleFoodRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    FoodService foodService;

    @Autowired
    FoodRepo foodRepo;

    @Test
    void contextLoads() {
//        Optional<Customer> optCustomer = customerRepo.findById("1");
//        System.out.println(optCustomer.get().getFullName());
//        Optional<Category> category = categoryRepo.findById(1);
//        System.out.println(category.get());
//        singleFoodRepo.updateSingleFoodIfRemoveCategory(2, 3);
        Cart cart = cartRepo.findCartByCustomerID("482be240-2f6a-4ebb-903e-f6ee37bdd3cc");
        System.out.println(cart.getCustomer().getFullName());
    }
    @Test
    void pagination() {
        Page<Food> foodPage = foodService.findPaginatedFood(1, 5, "soldQuantity", Utils.DESC_SORT);
        foodPage.forEach(food -> System.out.println(food.getID()));
    }

    @Test
    void salesOfFood() {
        List<Object[]> list = foodRepo.getSalesOfFood();
        for (Object[] objects : list) {
            System.out.println(objects[0].getClass());
        }
    }

    @Test
    void customerSpending() {
        List<Object[]> list = customerRepo.getCustomerSpending();
        System.out.println(list.size());
    }

}

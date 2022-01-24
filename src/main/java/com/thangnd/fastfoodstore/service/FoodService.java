package com.thangnd.fastfoodstore.service;

import com.thangnd.fastfoodstore.entity.food.Category;
import com.thangnd.fastfoodstore.entity.food.ComboFood;
import com.thangnd.fastfoodstore.entity.food.Food;
import com.thangnd.fastfoodstore.entity.food.SingleFood;
import com.thangnd.fastfoodstore.model.FoodStatistic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FoodService {
    List<List<Food>> getAllFood(String key, String sortField, String sortDirection);

    Food getFoodByID(int foodID);

    SingleFood saveSingleFood(SingleFood singleFood);

    ComboFood saveComboFood(ComboFood comboFood);

    SingleFood editSingleFood(SingleFood singleFood);

    ComboFood editComboFood(ComboFood comboFood);

    boolean deleteFood(int id);

    List<Category> getAllCategory();

    void updateFoodQuantityOfCategory(Category category);

    Category getCategoryByID(int id);

    SingleFood getSingleFoodByID(int id);

    List<SingleFood> getAllSingleFood();

    ComboFood getComboFoodByID(int id);

    Category saveCategory(Category category);

    void removeCategory(int id);

    List<Category> updateFoodQuantity(List<Category> categoryList);

    List<SingleFood> getBestSellerOfSingleFood();

    List<ComboFood> getBestSellerOfComboFood();

    Page<Food> findPaginatedFood(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Food> findPaginatedFoodByCategory(Category category, int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Food> findPaginatedFoodContainKeyName(String s, int pageNo, int pageSize, String s1, String s2);

    long getFoodTotal();

    List<FoodStatistic> getSalesForFood();

    List<List<Food>> getFoodByCategory(int categoryID, String key, String sortField, String sortDirection);
}

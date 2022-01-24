package com.thangnd.fastfoodstore.service;

import com.thangnd.fastfoodstore.model.FoodStatistic;
import com.thangnd.fastfoodstore.utils.Utils;
import com.thangnd.fastfoodstore.entity.food.Category;
import com.thangnd.fastfoodstore.entity.food.ComboFood;
import com.thangnd.fastfoodstore.entity.food.Food;
import com.thangnd.fastfoodstore.entity.food.SingleFood;
import com.thangnd.fastfoodstore.repository.CategoryRepo;
import com.thangnd.fastfoodstore.repository.ComboFoodRepo;
import com.thangnd.fastfoodstore.repository.FoodRepo;
import com.thangnd.fastfoodstore.repository.SingleFoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService{
    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private SingleFoodRepo singleFoodRepo;
    @Autowired
    private ComboFoodRepo comboFoodRepo;

    @Override
    public List<List<Food>> getAllFood(String key, String sortField, String sortDirection) {
        List<List<Food>> foodPages = new ArrayList<>();
        Sort sort = sortDirection.equalsIgnoreCase(Utils.DESC_SORT) ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        long totalFood = foodRepo.countAllByNameContains(key);
        int totalPage = (int) (totalFood / Utils.PAGE_FOOD_SIZE + 1);
        for (int i = 0; i < totalPage; i++) {
            Pageable pageable = PageRequest.of(i,Utils.PAGE_FOOD_SIZE, sort);
            Page<Food> foodPage = foodRepo.findAllByNameContains(key, pageable);
            foodPages.add(foodPage.getContent());
        }
        return foodPages;
    }

    @Override
    public List<List<Food>> getFoodByCategory(int categoryID, String key, String sortField, String sortDirection) {
        List<List<Food>> foodPages = new ArrayList<>();
        Sort sort = sortDirection.equalsIgnoreCase(Utils.DESC_SORT) ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        long totalFood = foodRepo.countAllByCategoryIDAndNameContains(categoryID, key);
        int totalPage = (int) (totalFood / Utils.PAGE_FOOD_SIZE + 1);
        for (int i = 0; i < totalPage; i++) {
            Pageable pageable = PageRequest.of(i,Utils.PAGE_FOOD_SIZE, sort);
            Page<Food> foodPage = foodRepo.findAllByCategoryIDAndNameContains(categoryID, key, pageable);
            foodPages.add(foodPage.getContent());
        }
        return foodPages;
    }

    @Override
    public Page<Food> findPaginatedFood(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Utils.DESC_SORT) ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return  foodRepo.findAll(pageable);
    }

    @Override
    public Page<Food> findPaginatedFoodByCategory(Category category, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Utils.DESC_SORT) ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return foodRepo.findAllByCategoryID(category.getID(),pageable);
    }

    @Override
    public Page<Food> findPaginatedFoodContainKeyName(String s, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Utils.DESC_SORT) ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return foodRepo.findAllByNameContains(s, pageable);
    }

    @Override
    public long getFoodTotal() {
        return foodRepo.count();
    }

    @Override
    public List<FoodStatistic> getSalesForFood() {
        List<Object[]> list = foodRepo.getSalesOfFood();
        List<FoodStatistic> foodStatisticList = new ArrayList<>();
        for (Object[] objects : list) {
            Food food = foodRepo.findById((Integer) objects[0]).get();
            foodStatisticList.add(new FoodStatistic(food, (double) objects[1]));
        }
        return foodStatisticList;
    }

    @Override
    public Food getFoodByID(int foodID) {
        return foodRepo.getById(foodID);
    }

    @Override
    public SingleFood saveSingleFood(SingleFood singleFood) {
        return foodRepo.save(singleFood);
    }

    @Override
    public ComboFood saveComboFood(ComboFood comboFood) {
        comboFood.setCategory(categoryRepo.findById(Utils.COMBO_TYPE).get());
        return foodRepo.save(comboFood);
    }

    @Override
    public SingleFood editSingleFood(SingleFood singleFood) {
        return foodRepo.save(singleFood);
    }

    @Override
    public ComboFood editComboFood(ComboFood comboFood) {
        return comboFoodRepo.save(comboFood);
    }

    @Override
    public boolean deleteFood(int id) {
        return false;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public void updateFoodQuantityOfCategory(Category category) {
        categoryRepo.save(category);
    }

    @Override
    public Category getCategoryByID(int id) {
        return categoryRepo.getById(id);
    }

    @Override
    public SingleFood getSingleFoodByID(int id) {
        return singleFoodRepo.findById(id).get();
    }

    @Override
    public List<SingleFood> getAllSingleFood() {
        return singleFoodRepo.findAll();
    }

    @Override
    public ComboFood getComboFoodByID(int id) {
        return comboFoodRepo.findById(id).get();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void removeCategory(int id) {
        singleFoodRepo.updateSingleFoodIfRemoveCategory(id, Utils.OTHER_TYPE);
        categoryRepo.deleteById(id);
    }

    @Override
    public List<Category> updateFoodQuantity(List<Category> categoryList) {
        for(Category category : categoryList) {
            category.setFoodQuantity(foodRepo.getFoodQuantityByCategoryID(category.getID()));
            categoryRepo.save(category);
        }
        return categoryList;
    }

    @Override
    public List<SingleFood> getBestSellerOfSingleFood() {
        return singleFoodRepo.findTop8ByOrderBySoldQuantityDesc();
    }

    @Override
    public List<ComboFood> getBestSellerOfComboFood() {
        return comboFoodRepo.findTop8ByOrderBySoldQuantityDesc();
    }
}

package com.thangnd.fastfoodstore.repository;

import com.thangnd.fastfoodstore.entity.food.ComboFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComboFoodRepo extends JpaRepository<ComboFood, Integer> {
    List<ComboFood> findTop8ByOrderBySoldQuantityDesc();
}

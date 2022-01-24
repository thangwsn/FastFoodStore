package com.thangnd.fastfoodstore.model;

import com.thangnd.fastfoodstore.entity.food.Food;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodStatistic {
    private Food food;
    private double salesAmount;
}

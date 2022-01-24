package com.thangnd.fastfoodstore.repository;

import com.thangnd.fastfoodstore.entity.food.SingleFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface SingleFoodRepo extends JpaRepository<SingleFood, Integer> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE SingleFood s SET s.category.ID = :newCategoryID WHERE s.category.ID = :categoryID")
    void updateSingleFoodIfRemoveCategory(@Param(value = "categoryID") int categoryID, @Param(value = "newCategoryID") int newCategoryID);

    List<SingleFood> findTop8ByOrderBySoldQuantityDesc();
}

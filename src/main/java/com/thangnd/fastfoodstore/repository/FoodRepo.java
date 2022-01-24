package com.thangnd.fastfoodstore.repository;

import com.thangnd.fastfoodstore.entity.food.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepo extends JpaRepository<Food, Integer> {
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM Food f WHERE CategoryID = :categoryID")
    int getFoodQuantityByCategoryID(@Param("categoryID") int id);
    Page<Food> findAllByCategoryID(int categoryID, Pageable pageable);
    Page<Food> findAllByNameContains(String key, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT f.ID, SUM(ol.quantity * ol.price) " +
            "FROM OrderLine ol, Food f " +
            "WHERE ol.foodID = f.ID " +
            "GROUP BY (f.ID) " +
            "ORDER BY SUM(ol.quantity * ol.price) DESC")
    List<Object[]> getSalesOfFood();
    Page<Food> findAllByCategoryIDAndNameContains(int categoryID, String key, Pageable pageable);
    long countAllByCategoryIDAndNameContains(int categoryID, String key);
    long countAllByNameContains(String key);
}

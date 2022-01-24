package com.thangnd.fastfoodstore.repository;

import com.thangnd.fastfoodstore.entity.food.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {
}

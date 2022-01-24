package com.thangnd.fastfoodstore.repository;

import com.thangnd.fastfoodstore.entity.order.CartLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartLineRepo extends JpaRepository<CartLine, String> {
}

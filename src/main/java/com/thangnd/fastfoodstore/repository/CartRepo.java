package com.thangnd.fastfoodstore.repository;

import com.thangnd.fastfoodstore.entity.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, String> {
    Cart findCartByCustomerID(String customerID);
}

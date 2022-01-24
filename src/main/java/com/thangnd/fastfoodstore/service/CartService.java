package com.thangnd.fastfoodstore.service;

import com.thangnd.fastfoodstore.entity.order.Cart;
import com.thangnd.fastfoodstore.entity.order.CartLine;
import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.entity.user.User;

public interface CartService {
    Cart getCartByCustomerID(String id);

    void addToCart(int foodID, int quantity, User user);

    CartLine getCartLineByID(String id);

    void removeFromCart(String cartLineID);

    void updateCart(Cart cart);

    Cart updateCartFilter(Customer customer);
}

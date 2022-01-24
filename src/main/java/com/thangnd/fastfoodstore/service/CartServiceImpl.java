package com.thangnd.fastfoodstore.service;

import com.thangnd.fastfoodstore.entity.food.Food;
import com.thangnd.fastfoodstore.entity.order.Cart;
import com.thangnd.fastfoodstore.entity.order.CartLine;
import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.repository.CartLineRepo;
import com.thangnd.fastfoodstore.repository.CartRepo;
import com.thangnd.fastfoodstore.repository.FoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private CartLineRepo cartLineRepo;

    @Override
    public Cart getCartByCustomerID(String id) {
        Cart cart = cartRepo.findCartByCustomerID(id);
        if (cart == null) {
            cart = new Cart();
            cart.setID(id);
            Customer customer = new Customer();
            customer.setID(id);
            cart.setCustomer(customer);
            cart.setCartLineList(new ArrayList<>());
            cart.setTotal(0);
            cartRepo.save(cart);
            return cartRepo.findCartByCustomerID(id);
        }
        return cart;
    }

    @Override
    public void addToCart(int foodID, int quantity, User user) {
        Cart cart = getCartByCustomerID(user.getID());

        List<CartLine> cartLineList = cart.getCartLineList();
        boolean isExisted = false;
        for (CartLine cartLine : cartLineList) {
            if (cartLine.getFood().getID() == foodID) {
                isExisted = true;
                cartLine.setQuantity(cartLine.getQuantity() + quantity);
                break;
            }
        }
        if (isExisted == false) {
            Food food = foodRepo.findById(foodID).get();
            CartLine cartLine = new CartLine();
            cartLine.setID(UUID.randomUUID().toString());
            cartLine.setCart(cart);
            cartLine.setFood(food);
            cartLine.setQuantity(quantity);
            cartLine.setPrice(food.getPrice());
            cartLineList.add(cartLine);
        }
        //set total
        cart.setCartLineList(cartLineList);
        cartRepo.save(cart);
    }

    @Override
    public CartLine getCartLineByID(String id) {
        return cartLineRepo.findById(id).get();
    }

    @Override
    public void removeFromCart(String cartLineID) {
        cartLineRepo.deleteById(cartLineID);
    }

    @Override
    public void updateCart(Cart cart) {
        List<CartLine> cartLineList = cart.getCartLineList();
        for (int i = 0; i < cartLineList.size(); i++) {
            if (cartLineList.get(i).getQuantity() == 0) {
                cartLineRepo.deleteById(cartLineList.get(i).getID());
                cartLineList.remove(i);
            }
        }
        cart.setCartLineList(cartLineList);
        cartRepo.save(cart);
    }

    @Override
    public Cart updateCartFilter(Customer customer) {
        Cart cart = getCartByCustomerID(customer.getID());
        double total = 0.0;
        List<CartLine> cartLineList = new ArrayList<>();
        if (cart.getCartLineList() != null) {
            cartLineList = cart.getCartLineList();
        }

        for (CartLine cartLine : cartLineList) {
            Food food = foodRepo.findById(cartLine.getFood().getID()).get();
            cartLine.setPrice(food.getPrice());
            total += cartLine.getQuantity() * cartLine.getPrice();
        }
        cart.setCartLineList(cartLineList);
        cart.setTotal(total);
        return cartRepo.save(cart);
    }
}

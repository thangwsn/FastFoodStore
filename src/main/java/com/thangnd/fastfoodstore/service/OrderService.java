package com.thangnd.fastfoodstore.service;

import com.thangnd.fastfoodstore.entity.order.Order;
import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.model.FoodStatistic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    void placeOrder(Order order, User user);

    List<Order> getAllOrderByCustomerID(Customer user);

    Order getOrderByID(String orderID);

    List<Order> getAllOrder();

    void updateOrderForStaff(String orderID, String orderStatus);

    Page<Order> findPaginatedOrder(int pageNo, int pageSize, String sortField, String sortDirection);

    long getTotalOrder();

    double getTotalSales();

    void setPaidForPayment(String orderID);

    void updateOrderForCustomer(String orderID, String orderStatus);
}

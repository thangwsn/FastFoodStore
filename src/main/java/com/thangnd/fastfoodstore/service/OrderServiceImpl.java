package com.thangnd.fastfoodstore.service;

import com.thangnd.fastfoodstore.entity.food.Food;
import com.thangnd.fastfoodstore.entity.order.*;
import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.repository.*;
import com.thangnd.fastfoodstore.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ShipmentRepo shipmentRepo;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private CartLineRepo cartLineRepo;

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void placeOrder(Order order, User user) {
        Customer customer = (Customer) user;
        order.setCustomer(customer);
        order.getPayment().setID(UUID.randomUUID().toString());
        order.getPayment().setStatus("Unpaid");
        String shipmentType = order.getShipment().getShipmentType();
        if (shipmentType.equals("GHTK")) {
            order.getPayment().setAmount(order.getTotal() + 20000);
        } else if (shipmentType.equals("GHN")){
            order.getPayment().setAmount(order.getTotal() + 25000);
        } else if (shipmentType.equals("SPX")) {
            order.getPayment().setAmount(order.getTotal() + 30000);
        }
        order.getShipment().setID(UUID.randomUUID().toString());
        order.setOrderStatusCustomer(OrderStatus.Pending);
        order.setOrderStatusStaff(OrderStatus.Pending);
        order.setCreateTime(new Timestamp(new Date().getTime()));
        paymentRepo.save(order.getPayment());
        shipmentRepo.save(order.getShipment());
        orderRepo.save(order);

        for (OrderLine orderLine : order.getOrderLineList()) {
            // update sold quantity of food
            Food food = foodRepo.findById(orderLine.getFood().getID()).get();
            food.setSoldQuantity(food.getSoldQuantity() + orderLine.getQuantity());
            foodRepo.save(food);
            // remove cart line from cart ; cart line id same order line id
            cartLineRepo.deleteById(orderLine.getID());
        }
    }

    @Override
    public List<Order> getAllOrderByCustomerID(Customer user) {
        return orderRepo.findAllByCustomerIDOrderByCreateTimeDesc(user.getID());
    }

    @Override
    public Order getOrderByID(String orderID) {
        return orderRepo.findById(orderID).get();
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepo.findAllByOrderByCreateTimeDesc();
    }

    @Override
    public void updateOrderForStaff(String orderID, String orderStatus) {
        Order order = orderRepo.getById(orderID);
        order.setOrderStatusStaff(OrderStatus.Completed);
        order.getPayment().setStatus("Paid");
        orderRepo.save(order);
    }

    @Override
    public Page<Order> findPaginatedOrder(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Utils.DESC_SORT) ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return orderRepo.findAll(pageable);
    }

    @Override
    public long getTotalOrder() {
        return orderRepo.count();
    }

    @Override
    public double getTotalSales() {
        if (orderRepo.getSales().isPresent()) {
            double sales = orderRepo.getSales().get();
            return sales;
        }
        return 0;
    }

    @Override
    public void setPaidForPayment(String orderID) {
        Order order = orderRepo.getById(orderID);
        Payment payment = order.getPayment();
        order.setOrderStatusCustomer(OrderStatus.Completed);
        payment.setStatus("Paid");
        orderRepo.save(order);
        paymentRepo.save(payment);
    }

    @Override
    public void updateOrderForCustomer(String orderID, String orderStatus) {
        Order order = orderRepo.getById(orderID);
        order.setOrderStatusCustomer(OrderStatus.Completed);
        order.getPayment().setStatus("Paid");
        orderRepo.save(order);
    }

}

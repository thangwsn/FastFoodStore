package com.thangnd.fastfoodstore.service;

import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.model.CustomerStatistic;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    boolean emailIsExist(String email);

    boolean mobileIsExist(String mobile);

    Page<Customer> findPaginatedCustomer(int pageNo, int pageSize, String sortField, String sortDirection);

    long getTotalCustomer();

    List<CustomerStatistic> getCustomerSpending();

    Customer getCustomerByID(String id);

    double getSpendingByCustomer(Customer customer);

    void editLevel(String s, String s1);
}

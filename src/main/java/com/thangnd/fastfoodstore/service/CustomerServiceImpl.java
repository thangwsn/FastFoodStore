package com.thangnd.fastfoodstore.service;

import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.entity.user.Role;
import com.thangnd.fastfoodstore.model.CustomerStatistic;
import com.thangnd.fastfoodstore.repository.CustomerRepo;
import com.thangnd.fastfoodstore.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer createCustomer(Customer customer) {
        customer.setID(UUID.randomUUID().toString());
        customer.setLevel("Member");
        customer.setRole(Role.Customer);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepo.save(customer);
    }

    @Override
    public boolean emailIsExist(String email) {
        return customerRepo.findByEmail(email) != null;
    }

    @Override
    public boolean mobileIsExist(String mobile) {
        return customerRepo.findByMobile(mobile) != null;
    }

    @Override
    public Page<Customer> findPaginatedCustomer(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Utils.DESC_SORT) ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return customerRepo.findAll(pageable);
    }

    @Override
    public long getTotalCustomer() {
        return customerRepo.count();
    }

    @Override
    public List<CustomerStatistic> getCustomerSpending() {
        List<Object[]> list = customerRepo.getCustomerSpending();
        List<CustomerStatistic> customerStatisticList = new ArrayList<>();
        for (Object[] objects : list) {
            Customer customer = customerRepo.findById((String) objects[0]).get();
            customerStatisticList.add(new CustomerStatistic(customer, (double) objects[1]));
        }
        return customerStatisticList;
    }

    @Override
    public Customer getCustomerByID(String id) {
        return customerRepo.findById(id).get();
    }

    @Override
    public double getSpendingByCustomer(Customer customer) {
        Optional<Double> spending = customerRepo.getSpendingByCustomerID(customer.getID());
        if (spending.isPresent()) {
            return spending.get();
        }
        return 0.0;
    }

    @Override
    public void editLevel(String customerID, String level) {
        Customer customer = customerRepo.findById(customerID).get();
        customer.setLevel(level);
        customerRepo.save(customer);
    }
}

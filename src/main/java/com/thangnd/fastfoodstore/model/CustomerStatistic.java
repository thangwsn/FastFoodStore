package com.thangnd.fastfoodstore.model;

import com.thangnd.fastfoodstore.entity.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerStatistic {
    private Customer customer;
    private double spending;
}

package com.thangnd.fastfoodstore.repository;

import com.thangnd.fastfoodstore.entity.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, String> {
}

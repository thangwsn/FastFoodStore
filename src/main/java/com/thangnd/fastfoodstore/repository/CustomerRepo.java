package com.thangnd.fastfoodstore.repository;

import com.thangnd.fastfoodstore.entity.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String> {
    Customer findByEmail(String email);
    Customer findByMobile(String mobile);
    Page<Customer> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT customer.ID, SUM(payment.amount) " +
                                        "FROM orders, customer, payment " +
                                        "WHERE orders.PaymentID = payment.ID " +
                                        "AND orders.CustomerID = customer.ID " +
                                        "GROUP BY customer.ID " +
                                        "ORDER BY SUM(payment.amount) DESC")
    List<Object[]> getCustomerSpending();

    @Query(nativeQuery = true, value = "SELECT SUM(payment.Amount) " +
                                        "FROM orders, payment " +
                                        "WHERE orders.CustomerID = :customerID " +
                                        "AND orders.PaymentID = payment.ID")
    Optional<Double> getSpendingByCustomerID(@Param("customerID") String customerID);
}

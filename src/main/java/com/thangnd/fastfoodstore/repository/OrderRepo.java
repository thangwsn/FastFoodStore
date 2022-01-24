package com.thangnd.fastfoodstore.repository;

import com.thangnd.fastfoodstore.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, String> {
    List<Order> findAllByCustomerIDOrderByCreateTimeDesc(String customerID);
    List<Order> findAllByOrderByCreateTimeDesc();

    @Modifying
    @Transactional
    @Query(value = "UPDATE Order o SET Status = :orderStatus WHERE o.ID = :orderID ")
    void updateOrderStatus(@Param("orderID") String orderID, @Param("orderStatus") String orderStatus);
    Page<Order> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT SUM(Total) FROM `orders` ")
    Optional<Double> getSales();

}

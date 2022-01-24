package com.thangnd.fastfoodstore.repository;

import com.thangnd.fastfoodstore.entity.order.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepo extends JpaRepository<Shipment, String> {
}

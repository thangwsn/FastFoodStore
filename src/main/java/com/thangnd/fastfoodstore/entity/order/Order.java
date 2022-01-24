package com.thangnd.fastfoodstore.entity.order;

import com.thangnd.fastfoodstore.entity.user.Customer;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "ID")
    private String ID;
    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID")
    private Customer customer;
    @OneToMany( mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<OrderLine> orderLineList;
    @Column(name = "Total")
    private double total;
    @OneToOne(targetEntity = Payment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PaymentID")
    private Payment payment;
    @OneToOne(targetEntity = Shipment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ShipmentID")
    private Shipment shipment;
    @Column(name = "StatusCustomer")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatusCustomer;
    @Column(name = "StatusStaff")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatusStaff;
    @Column(name = "CreateTime")
    private Timestamp createTime;

}

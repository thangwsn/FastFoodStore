package com.thangnd.fastfoodstore.entity.order;

import com.thangnd.fastfoodstore.entity.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "Total")
    private double total;
    @OneToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "CustomerID")
    private Customer customer;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", cascade = {CascadeType.ALL})
    @Fetch(FetchMode.JOIN)
    private List<CartLine> cartLineList;
    @Override
    public String toString() {
        return "";
    }
}

package com.thangnd.fastfoodstore.entity.user;

import com.thangnd.fastfoodstore.entity.order.Cart;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends User {
    @Column(name = "Level")
    private String level;
}

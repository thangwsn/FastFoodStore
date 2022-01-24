package com.thangnd.fastfoodstore.entity.order;

import com.thangnd.fastfoodstore.entity.food.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cartline")
public class CartLine {
    @Id
    @Column(name = "ID")
    private String ID;
    @ManyToOne(targetEntity = Cart.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "CartID")
    private Cart cart;
    @ManyToOne(targetEntity = Food.class, fetch =  FetchType.LAZY)
    @JoinColumn(name = "FoodID")
    private Food food;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Price")
    private double price;

}

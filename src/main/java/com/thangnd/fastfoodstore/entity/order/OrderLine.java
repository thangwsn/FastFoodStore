package com.thangnd.fastfoodstore.entity.order;

import com.thangnd.fastfoodstore.entity.food.Food;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orderline")
public class OrderLine {
    @Id
    @Column(name = "ID")
    private String ID;
    @ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID")
    private Order order;
    @ManyToOne(targetEntity = Food.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "FoodID")
    private Food food;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Price")
    private double price;
}

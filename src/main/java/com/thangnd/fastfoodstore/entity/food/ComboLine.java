package com.thangnd.fastfoodstore.entity.food;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comboline")
public class ComboLine {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @ManyToOne(targetEntity = SingleFood.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "SingleFoodID")
    private SingleFood singleFood;
    @ManyToOne(targetEntity = ComboFood.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ComboFoodID")
    private ComboFood comboFood;
    @Column(name = "Quantity")
    private int quantity;

    public ComboLine(SingleFood singleFood, ComboFood comboFood, int quantity) {
        this.singleFood = singleFood;
        this.quantity = quantity;
        this.comboFood = comboFood;
    }

    @Override
    public String toString() {
        return "";
    }

}

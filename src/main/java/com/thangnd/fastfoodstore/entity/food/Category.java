package com.thangnd.fastfoodstore.entity.food;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Column(name = "Name")
    @NotBlank(message = "Category name can not be blank!")
    private String name;
    @Column
    @Min(0)
    private int foodQuantity;
}

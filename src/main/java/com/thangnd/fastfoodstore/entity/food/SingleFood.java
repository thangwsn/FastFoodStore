package com.thangnd.fastfoodstore.entity.food;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "singlefood")
public class SingleFood extends Food {
    @Column(name = "Calories")
    @Min(0)
    private int calories;
    public String toString() {
        return "";
    }
}

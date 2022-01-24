package com.thangnd.fastfoodstore.entity.user;

import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "staff")
public class Staff extends User{
    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;
    @Column(name = "BaseSalary")
    private double baseSalary;
}

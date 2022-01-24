package com.thangnd.fastfoodstore.entity.order;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "Amount")
    private double amount;
    @Column(name = "Method")
    private String method;
    @Column(name = "Status")
    private String status;
}

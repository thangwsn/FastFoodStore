package com.thangnd.fastfoodstore.entity.order;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipment")
public class Shipment {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "FullName")
    @NotBlank(message = "Full name can not be blank!")
    private String fullName;
    @Column(name = "Mobile")
    @Pattern(regexp = "[\\d]{10}", message = "Mobile is not valid!")
    private String mobile;
    @Column(name = "Address")
    @NotBlank(message = "Address can not be blank!")
    private String address;
    @Column(name = "Note")
    private String note;
    @Column(name = "ShipmentType")
    private String shipmentType;
}

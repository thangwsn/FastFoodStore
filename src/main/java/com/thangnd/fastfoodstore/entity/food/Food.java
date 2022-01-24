package com.thangnd.fastfoodstore.entity.food;

import com.thangnd.fastfoodstore.entity.order.CartLine;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int ID;
    @Column(name = "Name")
    @NotBlank(message = "Food Name can not be blank!")
    private String name;
    @Column(name = "Description")
    @NotBlank(message = "Description can not be blank!")
    private String description;
    @Column(name = "Price")
    @Min(0)
    private double price;
    @Column(name = "SoldQuantity")
    private int soldQuantity;
    @Column(name = "Image")
    private String image;
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    private Category category;
    @Transient
    private MultipartFile imageFile;

}

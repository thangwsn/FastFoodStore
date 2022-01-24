package com.thangnd.fastfoodstore.entity.user;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Mỗi class là một bảng, yêu cầu join bảng. https://techmaster.vn/posts/36499/hibernate-inheritance-mapping
@Table(name = "user")
public class User {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "Email", unique = true)
    @Email(message = "Email is not valid!")
    private String email;
    @Column(name = "Password")
    private String password;
    @Column(name = "FullName")
    @NotBlank(message = "Full name can not be blank!")
    private String fullName;
    @Column(name = "Gender")
    private String gender;
    @Pattern(regexp = "[\\d]{10}", message = "Mobile is not valid!")
    @Column(name = "Mobile")
    private String mobile;
    @Column(name = "Address")
    @NotBlank(message = "Address can not be blank!")
    private String address;
    @Column(name = "Dob")
    private Date dob;
    @DateTimeFormat(pattern = "yyyy-dd-MM")
    @Column(name = "Role")
    @Enumerated(EnumType.STRING)
    private Role role;
}

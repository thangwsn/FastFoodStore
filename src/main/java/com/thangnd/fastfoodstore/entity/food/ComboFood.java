package com.thangnd.fastfoodstore.entity.food;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "combofood")
public class ComboFood extends Food{
    @OneToMany(targetEntity = ComboLine.class, mappedBy = "comboFood", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<ComboLine> comboLineList;
}

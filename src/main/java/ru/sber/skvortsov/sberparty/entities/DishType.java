package ru.sber.skvortsov.sberparty.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dish_type_tbl")
@NoArgsConstructor
@Data
public class DishType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_type_id")
    private Long id;

    @Column(name = "title_fld")
    private String title;

    @OneToMany(mappedBy = "dishType")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Dish> dishes;
}

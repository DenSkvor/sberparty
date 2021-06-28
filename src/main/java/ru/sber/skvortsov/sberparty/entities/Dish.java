package ru.sber.skvortsov.sberparty.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "dish_tbl")
@NoArgsConstructor
@Data
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private Long id;

    @Column(name = "title_fld")
    private String title;

    @Column(name = "description_fld")
    private String description;

    @Column(name = "price_fld")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "dish_type_id")
    private DishType dishType;

    @OneToMany(mappedBy = "dish")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<MenuOrderItem> menuOrderItems;
}

package ru.sber.skvortsov.sberparty.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant_tbl")
@NoArgsConstructor
@Data
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(name = "title_fld")
    private String title;

    @Column(name = "description_fld")
    private String description;

    @OneToMany(mappedBy = "restaurant")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<RestaurantHall> restaurantHalls;

    @OneToMany(mappedBy = "restaurant")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Dish> dishes;
/*
    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantLike> restaurantLikes;
*/
    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders;

}

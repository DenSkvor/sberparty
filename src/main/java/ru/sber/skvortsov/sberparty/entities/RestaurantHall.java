package ru.sber.skvortsov.sberparty.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "restaurant_hall_tbl")
@NoArgsConstructor
@Data
public class RestaurantHall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_hall_id")
    private Long id;

    @Column(name = "place_count_fld")
    private int placeCount;

    @Column(name = "price_fld")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "restaurantHall")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Order> orders;
}

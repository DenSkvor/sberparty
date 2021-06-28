package ru.sber.skvortsov.sberparty.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_order_item_tbl")
@NoArgsConstructor
@Data
public class MenuOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_order_item_id")
    private Long id;

    @Column(name = "count_fld")
    private int count;

    @Column(name = "total_cost_fld")
    private BigDecimal totalCost;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Order order;
}

package ru.sber.skvortsov.sberparty.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_tbl")
@NoArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "description_fld")
    private String description;
/*
    @Column(name = "date_time_fld")
    private LocalDateTime dateTime;
*/
    @Column(name = "total_cost_fld")
    private BigDecimal totalCost;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "party_type_id")
    private PartyType partyType;

    @ManyToOne
    @JoinColumn(name = "audience_type_id")
    private AudienceType audienceType;

    @ManyToOne
    @JoinColumn(name = "party_host_id")
    private PartyHost partyHost;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "restaurant_hall_id")
    private RestaurantHall restaurantHall;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<MenuOrderItem> menuOrderItems;
}

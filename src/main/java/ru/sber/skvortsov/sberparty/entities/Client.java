package ru.sber.skvortsov.sberparty.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client_tbl")
@NoArgsConstructor
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Column(name = "firstname_fld")
    private String firstname;

    @Column(name = "lastname_fld")
    private String lastname;
/*
    @OneToMany(mappedBy = "client")
    private List<PartyHostLike> partyHostLikes;

    @OneToMany(mappedBy = "client")
    private List<RestaurantLike> restaurantLikes;
*/
    @OneToMany(mappedBy = "client")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Order> orders;
}

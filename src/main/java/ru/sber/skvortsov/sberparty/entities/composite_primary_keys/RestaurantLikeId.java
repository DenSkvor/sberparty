/*package ru.sber.skvortsov.sberparty.entities.composite_primary_keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.sber.skvortsov.sberparty.entities.Client;
import ru.sber.skvortsov.sberparty.entities.Restaurant;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Embeddable
public class RestaurantLikeId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}

 */

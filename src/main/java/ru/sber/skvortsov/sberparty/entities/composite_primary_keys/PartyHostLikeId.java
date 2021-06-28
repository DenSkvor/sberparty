/*package ru.sber.skvortsov.sberparty.entities.composite_primary_keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.sber.skvortsov.sberparty.entities.Client;
import ru.sber.skvortsov.sberparty.entities.PartyHost;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class PartyHostLikeId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "party_host_id")
    private PartyHost partyHost;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}

 */

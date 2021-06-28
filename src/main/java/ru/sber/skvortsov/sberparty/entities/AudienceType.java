package ru.sber.skvortsov.sberparty.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "audience_type_tbl")
@NoArgsConstructor
@Data
public class AudienceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audience_type_id")
    private Long id;

    @Column(name = "title_fld")
    private String title;

    @ManyToMany
    @JoinTable(
            name = "party_host_audience_type_m2m_tbl",
            joinColumns = @JoinColumn(name = "audience_type_id"),
            inverseJoinColumns = @JoinColumn(name = "party_host_id")
    )
    private List<PartyHost> partyHosts;

    @OneToMany(mappedBy = "audienceType")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Order> orders;

    /*
    @OneToMany(mappedBy = "audienceType")
    private List<PartyHostAudienceTypeM2M> partyHosts;

     */
}

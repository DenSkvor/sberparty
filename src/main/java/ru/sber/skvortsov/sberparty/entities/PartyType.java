package ru.sber.skvortsov.sberparty.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "party_type_tbl")
@NoArgsConstructor
@Data
public class PartyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_type_id")
    private Long id;

    @Column(name = "title_fld")
    private String title;

    @ManyToMany
    @JoinTable(
            name = "party_host_paty_type_m2m_tbl",
            joinColumns = @JoinColumn(name = "party_type_id"),
            inverseJoinColumns = @JoinColumn(name = "party_host_id")
    )
    private List<PartyHost> partyHosts;

    @OneToMany(mappedBy = "partyType")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Order> orders;

    /*
    @OneToMany(mappedBy = "partyType")
    private List<PartyHostPartyTypeM2M> partyHosts;
     */
}

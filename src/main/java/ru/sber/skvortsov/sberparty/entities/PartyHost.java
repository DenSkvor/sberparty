package ru.sber.skvortsov.sberparty.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "party_host_tbl")
@NoArgsConstructor
@Data
public class PartyHost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_host_id")
    private Long id;

    @Column(name = "firstname_fld")
    private String firstname;

    @Column(name = "lastname_fld")
    private String lastname;

    @Column(name = "description_fld")
    private String description;

    @Column(name = "price_fld")
    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name = "party_host_paty_type_m2m_tbl",
            joinColumns = @JoinColumn(name = "party_host_id"),
            inverseJoinColumns = @JoinColumn(name = "party_type_id")
    )
    private List<PartyType> partyTypes;

    @ManyToMany
    @JoinTable(
            name = "party_host_audience_type_m2m_tbl",
            joinColumns = @JoinColumn(name = "party_host_id"),
            inverseJoinColumns = @JoinColumn(name = "audience_type_id")
    )
    private List<AudienceType> audienceTypes;

    /*
    @OneToMany(mappedBy = "partyHost")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<PartyHostPartyTypeM2M> partyTypes;

    @OneToMany(mappedBy = "partyHost")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<PartyHostAudienceTypeM2M> audienceTypes;


    @OneToMany(mappedBy = "partyHost")
    private List<PartyHostLike> partyHostLikes;

     */

    @OneToMany(mappedBy = "partyHost")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Order> orders;

}

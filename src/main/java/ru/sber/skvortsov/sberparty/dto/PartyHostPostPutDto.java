package ru.sber.skvortsov.sberparty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.skvortsov.sberparty.entities.Order;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PartyHostPostPutDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String description;
    private BigDecimal price;
    private List<PartyTypeOnlyIdDto> partyTypes;
    private List<AudienceTypeOnlyIdDto> audienceTypes;
    //private List<Order> orders;
}

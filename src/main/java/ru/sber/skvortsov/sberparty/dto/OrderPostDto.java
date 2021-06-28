package ru.sber.skvortsov.sberparty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPostDto {

    private Long id;
    private String description;
    private BigDecimal totalCost;
    private ClientOnlyIdDto client;
    private PartyTypeOnlyIdDto partyType;
    private AudienceTypeOnlyIdDto audienceType;
    private PartyHostOnlyIdDto partyHost;
    private RestaurantOnlyIdDto restaurant;
    private RestaurantHallOnlyIdDto restaurantHall;
    private List<MenuOrderItemPostDto> menuOrderItems;
}

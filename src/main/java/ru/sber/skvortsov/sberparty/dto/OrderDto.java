package ru.sber.skvortsov.sberparty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDto {

    private Long id;
    private String description;
    private BigDecimal totalCost;
    private ClientWithoutOrderDto client;
    private PartyTypeWithoutBondsDto partyType;
    private AudienceTypeWithoutBondsDto audienceType;
    private PartyHostDto partyHost;
    private RestaurantWithoutBondsDto restaurant;
    private RestaurantHallWithoutRestaurantDto restaurantHall;
    private List<MenuOrderItemDto> menuOrderItems;
}

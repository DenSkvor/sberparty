package ru.sber.skvortsov.sberparty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantHallDto {
    private Long id;
    private int placeCount;
    private BigDecimal price;
    private RestaurantWithoutBondsDto restaurant;
}

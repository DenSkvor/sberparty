package ru.sber.skvortsov.sberparty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuOrderItemPostDto {

    private Long id;
    private int count;
    private BigDecimal totalCost;
    private DishOnlyIdDto dish;
    private OrderOnlyIdDto order;


}

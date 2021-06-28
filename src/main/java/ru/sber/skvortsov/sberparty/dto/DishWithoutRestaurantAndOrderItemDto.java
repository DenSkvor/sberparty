package ru.sber.skvortsov.sberparty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.skvortsov.sberparty.entities.DishType;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DishWithoutRestaurantAndOrderItemDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private DishTypeWithoutDishesDto dishType;

}

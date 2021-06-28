package ru.sber.skvortsov.sberparty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.skvortsov.sberparty.entities.MenuOrderItem;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DishPostPutDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private RestaurantOnlyIdDto restaurant;
    private DishTypeOnlyIdDto dishType;

}

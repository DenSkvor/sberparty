package ru.sber.skvortsov.sberparty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.skvortsov.sberparty.entities.Order;
import ru.sber.skvortsov.sberparty.entities.RestaurantHall;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantDto {

    private Long id;
    private String title;
    private String description;
    private List<RestaurantHallWithoutRestaurantDto> restaurantHalls;
    private List<DishWithoutRestaurantAndOrderItemDto> dishes;
    //private List<Order> orders;

}

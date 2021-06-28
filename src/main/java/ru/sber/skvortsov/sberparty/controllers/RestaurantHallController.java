package ru.sber.skvortsov.sberparty.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import ru.sber.skvortsov.sberparty.dto.RestaurantHallDto;
import ru.sber.skvortsov.sberparty.dto.RestaurantHallPostPutDto;
import ru.sber.skvortsov.sberparty.services.RestaurantHallService;
import ru.sber.skvortsov.sberparty.utils.KafkaMessage;
import ru.sber.skvortsov.sberparty.utils.KafkaMessageProducer;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.Endpoints.AUDIENCE_TYPE_ENDPOINT;
import static ru.sber.skvortsov.sberparty.utils.Endpoints.RESTAURANT_HALL_ENDPOINT;

@RestController
@RequestMapping(RESTAURANT_HALL_ENDPOINT)
@AllArgsConstructor
@Log4j2
@Tag(name = "Залы ресторана", description = "Работа с сущностью 'зал ресторана': получение, создание, изменение, удаление")
public class RestaurantHallController {

    private final RestaurantHallService restaurantHallService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/{id}")
    @Operation(summary = "Получение зала по id")
    public RestaurantHallDto getById(@PathVariable @Parameter(description = "Идентификатор зала") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.GET, RESTAURANT_HALL_ENDPOINT, id));
        RestaurantHallDto returnDto = restaurantHallService.findById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, RESTAURANT_HALL_ENDPOINT+id, returnDto));
        return returnDto;
    }

    @GetMapping
    @Operation(summary = "Получение всех залов")
    public List<RestaurantHallDto> getAll(){
        log.info(String.format("%s: %s", HttpMethod.GET, RESTAURANT_HALL_ENDPOINT));
        List<RestaurantHallDto> returnListDto = restaurantHallService.findAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, RESTAURANT_HALL_ENDPOINT, returnListDto));
        return returnListDto;
    }

    @PostMapping
    @Operation(summary = "Добавление нового зала")
    public RestaurantHallPostPutDto create(@RequestBody RestaurantHallPostPutDto restaurantHallPostPutDto){
        log.info(String.format("%s: %s", HttpMethod.POST, RESTAURANT_HALL_ENDPOINT));
        RestaurantHallPostPutDto returnDto = restaurantHallService.create(restaurantHallPostPutDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.POST, RESTAURANT_HALL_ENDPOINT, returnDto));
        return returnDto;
    }

    @PutMapping
    @Operation(summary = "Изменение имеющегося зала")
    public RestaurantHallPostPutDto update(@RequestBody RestaurantHallPostPutDto restaurantHallPostPutDto){
        log.info(String.format("%s: %s", HttpMethod.PUT, RESTAURANT_HALL_ENDPOINT));
        RestaurantHallPostPutDto returnDto = restaurantHallService.update(restaurantHallPostPutDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.PUT, RESTAURANT_HALL_ENDPOINT, returnDto));
        return returnDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление зала по id")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор зала") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.DELETE, RESTAURANT_HALL_ENDPOINT, id));
        restaurantHallService.deleteById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, RESTAURANT_HALL_ENDPOINT+id, ""));
    }

    @DeleteMapping
    @Operation(summary = "Удаление всех залов")
    public void deleteAll(){
        log.info(String.format("%s: %s", HttpMethod.DELETE, RESTAURANT_HALL_ENDPOINT));
        restaurantHallService.deleteAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, RESTAURANT_HALL_ENDPOINT, ""));
    }
}

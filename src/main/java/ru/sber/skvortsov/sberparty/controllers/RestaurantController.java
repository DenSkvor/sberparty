package ru.sber.skvortsov.sberparty.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sber.skvortsov.sberparty.dto.RestaurantDto;
import ru.sber.skvortsov.sberparty.dto.RestaurantWithoutBondsDto;
import ru.sber.skvortsov.sberparty.services.RestaurantService;
import ru.sber.skvortsov.sberparty.utils.KafkaMessage;
import ru.sber.skvortsov.sberparty.utils.KafkaMessageProducer;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.Endpoints.RESTAURANT_ENDPOINT;

@RestController
@RequestMapping(RESTAURANT_ENDPOINT)
@AllArgsConstructor
@Log4j2
@Tag(name = "Рестораны (Место проведения праздничного мероприятия)", description = "Работа с сущностью 'ресторан': получение, создание, изменение, удаление")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/{id}")
    @Operation(summary = "Получение ресторана по id")
    public RestaurantDto getById(@PathVariable @Parameter(description = "Идентификатор ресторана") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.GET, RESTAURANT_ENDPOINT, id));
        RestaurantDto returnDto = restaurantService.findById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, RESTAURANT_ENDPOINT+id, returnDto));
        return returnDto;
    }

    @GetMapping()
    @Operation(summary = "Получение всех ресторанов")
    public List<RestaurantDto> getAll(){
        log.info(String.format("%s: %s", HttpMethod.GET, RESTAURANT_ENDPOINT));
        List<RestaurantDto> returnListDto = restaurantService.findAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, RESTAURANT_ENDPOINT, returnListDto));
        return returnListDto;
    }

    @PostMapping
    @Operation(summary = "Добавление нового ресторана")
    public RestaurantWithoutBondsDto create(@RequestBody RestaurantWithoutBondsDto restaurantWithoutBondsDto){
        log.info(String.format("%s: %s", HttpMethod.POST, RESTAURANT_ENDPOINT));
        RestaurantWithoutBondsDto returnDto = restaurantService.create(restaurantWithoutBondsDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.POST, RESTAURANT_ENDPOINT, returnDto));
        return returnDto;
    }

    @PutMapping
    @Operation(summary = "Изменение имеющегося ресторана")
    public RestaurantWithoutBondsDto update(@RequestBody RestaurantWithoutBondsDto restaurantWithoutBondsDto){
        log.info(String.format("%s: %s", HttpMethod.PUT, RESTAURANT_ENDPOINT));
        RestaurantWithoutBondsDto returnDto = restaurantService.update(restaurantWithoutBondsDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.PUT, RESTAURANT_ENDPOINT, returnDto));
        return returnDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ресторана по id")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор ресторана") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.DELETE, RESTAURANT_ENDPOINT, id));
        restaurantService.deleteById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, RESTAURANT_ENDPOINT+id, ""));
    }

    @DeleteMapping()
    @Operation(summary = "Удаление всех ресторанов")
    public void deleteAll(){
        log.info(String.format("%s: %s", HttpMethod.DELETE, RESTAURANT_ENDPOINT));
        restaurantService.deleteAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, RESTAURANT_ENDPOINT, ""));
    }

}

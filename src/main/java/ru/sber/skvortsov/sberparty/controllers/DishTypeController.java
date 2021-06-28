package ru.sber.skvortsov.sberparty.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import ru.sber.skvortsov.sberparty.dto.DishTypeWithoutDishesDto;
import ru.sber.skvortsov.sberparty.services.DishTypeService;
import ru.sber.skvortsov.sberparty.utils.KafkaMessage;
import ru.sber.skvortsov.sberparty.utils.KafkaMessageProducer;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.Endpoints.DISH_TYPE_ENDPOINT;

@RestController
@RequestMapping(DISH_TYPE_ENDPOINT)
@AllArgsConstructor
@Log4j2
@Tag(name = "Типы блюд", description = "Работа с сущностью 'тип блюда': получение, создание, изменение, удаление")
public class DishTypeController {

    private final DishTypeService dishTypeService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/{id}")
    @Operation(summary = "Получение типа блюда по id")
    public DishTypeWithoutDishesDto getById(@PathVariable @Parameter(description = "Идентификатор типа блюда") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.GET, DISH_TYPE_ENDPOINT, id));
        DishTypeWithoutDishesDto returnDto = dishTypeService.findById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, DISH_TYPE_ENDPOINT+id, returnDto));
        return returnDto;
    }

    @GetMapping
    @Operation(summary = "Получение всех типов блюд")
    public List<DishTypeWithoutDishesDto> getAll(){
        log.info(String.format("%s: %s", HttpMethod.GET, DISH_TYPE_ENDPOINT));
        List<DishTypeWithoutDishesDto> returnListDto = dishTypeService.findAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, DISH_TYPE_ENDPOINT, returnListDto));
        return returnListDto;
    }

    @PostMapping
    @Operation(summary = "Добавление нового типа блюда")
    public DishTypeWithoutDishesDto create(@RequestBody DishTypeWithoutDishesDto dishTypeWithoutDishesDto){
        log.info(String.format("%s: %s", HttpMethod.POST, DISH_TYPE_ENDPOINT));
        DishTypeWithoutDishesDto returnDto = dishTypeService.create(dishTypeWithoutDishesDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.POST, DISH_TYPE_ENDPOINT, returnDto));
        return returnDto;
    }

    @PutMapping
    @Operation(summary = "Изменение имеющегося типа клиента")
    public DishTypeWithoutDishesDto update(@RequestBody DishTypeWithoutDishesDto dishTypeWithoutDishesDto){
        log.info(String.format("%s: %s", HttpMethod.PUT, DISH_TYPE_ENDPOINT));
        DishTypeWithoutDishesDto returnDto = dishTypeService.update(dishTypeWithoutDishesDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.PUT, DISH_TYPE_ENDPOINT, returnDto));
        return returnDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление типа блюда по id")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор типа блюда") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.DELETE, DISH_TYPE_ENDPOINT, id));
        dishTypeService.deleteById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, DISH_TYPE_ENDPOINT+id, ""));
    }

    @DeleteMapping
    @Operation(summary = "Удаление всех типов блюд")
    public void deleteAll(){
        log.info(String.format("%s: %s", HttpMethod.DELETE, DISH_TYPE_ENDPOINT));
        dishTypeService.deleteAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, DISH_TYPE_ENDPOINT, ""));
    }
}

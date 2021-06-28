package ru.sber.skvortsov.sberparty.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sber.skvortsov.sberparty.dto.DishDto;
import ru.sber.skvortsov.sberparty.dto.DishPostPutDto;
import ru.sber.skvortsov.sberparty.services.DishService;
import ru.sber.skvortsov.sberparty.utils.KafkaMessage;
import ru.sber.skvortsov.sberparty.utils.KafkaMessageProducer;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.Endpoints.DISH_ENDPOINT;

@RestController
@RequestMapping(DISH_ENDPOINT)
@AllArgsConstructor
@Log4j2
@Tag(name = "Блюда", description = "Работа с сущностью 'блюдо': получение, создание, изменение, удаление")
public class DishController {

    private final DishService dishService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/{id}")
    @Operation(summary = "Получение блюда по id")
    public DishDto getById(@PathVariable @Parameter(description = "Идентификатор клиента") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.GET, DISH_ENDPOINT, id));
        DishDto returnDto = dishService.findById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, DISH_ENDPOINT+id, returnDto));
        return returnDto;
    }

    @GetMapping()
    @Operation(summary = "Получение всех блюд")
    public List<DishDto> getAll(){
        log.info(String.format("%s: %s", HttpMethod.GET, DISH_ENDPOINT));
        List<DishDto> returnListDto = dishService.findAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, DISH_ENDPOINT, returnListDto));
        return returnListDto;
    }

    @PostMapping
    @Operation(summary = "Добавление нового блюда")
    public DishPostPutDto create(@RequestBody DishPostPutDto dishPostPutDto){
        log.info(String.format("%s: %s", HttpMethod.POST, DISH_ENDPOINT));
        DishPostPutDto returnDto = dishService.create(dishPostPutDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.POST, DISH_ENDPOINT, returnDto));
        return returnDto;
    }

    @PutMapping
    @Operation(summary = "Изменение имеющегося блюда")
    public DishPostPutDto update(@RequestBody DishPostPutDto dishPostPutDto){
        log.info(String.format("%s: %s", HttpMethod.PUT, DISH_ENDPOINT));
        DishPostPutDto returnDto = dishService.update(dishPostPutDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.PUT, DISH_ENDPOINT, returnDto));
        return returnDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление блюда по id")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор блюда") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.DELETE, DISH_ENDPOINT, id));
        dishService.deleteById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, DISH_ENDPOINT+id, ""));
    }

    @DeleteMapping
    @Operation(summary = "Удаление всех блюд")
    public void deleteAll(){
        log.info(String.format("%s: %s", HttpMethod.DELETE, DISH_ENDPOINT));
        dishService.deleteAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, DISH_ENDPOINT, ""));
    }
}

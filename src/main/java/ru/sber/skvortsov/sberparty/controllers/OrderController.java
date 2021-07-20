package ru.sber.skvortsov.sberparty.controllers;


import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import ru.sber.skvortsov.sberparty.dto.OrderDto;
import ru.sber.skvortsov.sberparty.dto.OrderPostDto;
import ru.sber.skvortsov.sberparty.services.OrderService;
import ru.sber.skvortsov.sberparty.utils.KafkaMessage;
import ru.sber.skvortsov.sberparty.utils.KafkaMessageProducer;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.Endpoints.ORDER_ENDPOINT;

@RestController
@RequestMapping(ORDER_ENDPOINT)
@Log4j2
@AllArgsConstructor
@Tag(name = "Заказы", description = "Работа с сущностью 'заказ': получение, создание, удаление")
public class OrderController {

    private final OrderService orderService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/{id}")
    @Operation(summary = "Получение заказа по id")
    public OrderDto getById(@PathVariable @Parameter(description = "Идентификатор заказа") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.GET, ORDER_ENDPOINT, id));
        OrderDto returnDto = orderService.findById(id);
        //System.out.println(kafkaMessageProducer.convertToJson(new KafkaMessage<>(HttpMethod.GET, ORDER_ENDPOINT+id, returnDto)));
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, ORDER_ENDPOINT+id, returnDto));
        return returnDto;
    }

    @GetMapping
    @Operation(summary = "Получение всех заказов")
    public List<OrderDto> getAll(){
        log.info(String.format("%s: %s", HttpMethod.GET, ORDER_ENDPOINT));
        List<OrderDto> returnListDto = orderService.findAll();
        //System.out.println(kafkaMessageProducer.convertToJson(new KafkaMessage<>(HttpMethod.GET, ORDER_ENDPOINT, returnListDto)));
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, ORDER_ENDPOINT, returnListDto));
        return returnListDto;
    }

    @PostMapping
    @Operation(summary = "Добавление нового заказа")
    public OrderPostDto create(@RequestBody OrderPostDto orderPostDto){
        log.info(String.format("%s: %s", HttpMethod.POST, ORDER_ENDPOINT));
        OrderPostDto returnDto = orderService.create(orderPostDto);
        //System.out.println(kafkaMessageProducer.convertToJson(new KafkaMessage<>(HttpMethod.POST, ORDER_ENDPOINT, returnDto)));
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.POST, "/order", orderPostDto));
        return returnDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление заказа по id")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор заказа") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.DELETE, ORDER_ENDPOINT, id));
        orderService.deleteById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, ORDER_ENDPOINT+id, ""));
    }

    @DeleteMapping
    @Operation(summary = "Удаление всех заказов")
    public void deleteAll(){
        log.info(String.format("%s: %s", HttpMethod.DELETE, ORDER_ENDPOINT));
        orderService.deleteAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, ORDER_ENDPOINT, ""));
    }

}

package ru.sber.skvortsov.sberparty.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.skvortsov.sberparty.dto.AudienceTypeWithoutBondsDto;
import ru.sber.skvortsov.sberparty.dto.MenuOrderItemDto;
import ru.sber.skvortsov.sberparty.entities.MenuOrderItem;
import ru.sber.skvortsov.sberparty.services.MenuOrderItemService;
import ru.sber.skvortsov.sberparty.utils.KafkaMessage;
import ru.sber.skvortsov.sberparty.utils.KafkaMessageProducer;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.Endpoints.MENU_ORDER_ITEM_ENDPOINT;

@RestController
@RequestMapping(MENU_ORDER_ITEM_ENDPOINT)
@AllArgsConstructor
@Log4j2
@Tag(name = "Пункты меню в заказе", description = "Работа с сущностью 'пункт меню в заказе': получение. Создание, изменение и удаление производятся только каскадно через сущность 'заказ'")
public class MenuOrderItemController {

    private final MenuOrderItemService menuOrderItemService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/{id}")
    @Operation(summary = "Получение пункта меню в заказе по id")
    public MenuOrderItemDto getById(@PathVariable @Parameter(description = "Идентификатор пункта меню в заказе") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.GET, MENU_ORDER_ITEM_ENDPOINT, id));
        MenuOrderItemDto returnDto = menuOrderItemService.findById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, MENU_ORDER_ITEM_ENDPOINT+id, returnDto));
        return returnDto;
    }

    @GetMapping
    @Operation(summary = "Получение всех пунктов меню в заказе")
    public List<MenuOrderItemDto> getAll(){
        log.info(String.format("%s: %s", HttpMethod.GET, MENU_ORDER_ITEM_ENDPOINT));
        List<MenuOrderItemDto> returnListDto = menuOrderItemService.findAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, MENU_ORDER_ITEM_ENDPOINT, returnListDto));
        return returnListDto;
    }
}

package ru.sber.skvortsov.sberparty.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import ru.sber.skvortsov.sberparty.dto.AudienceTypeWithoutBondsDto;
import ru.sber.skvortsov.sberparty.services.AudienceTypeService;
import ru.sber.skvortsov.sberparty.utils.KafkaMessage;
import ru.sber.skvortsov.sberparty.utils.KafkaMessageProducer;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.Endpoints.AUDIENCE_TYPE_ENDPOINT;


@RestController
@RequestMapping(AUDIENCE_TYPE_ENDPOINT)
@AllArgsConstructor
@Log4j2
@Tag(name = "Типы публики мероприятия", description = "Работа с сущностью 'тип публики': получение, создание, изменение, удаление")
public class AudienceTypeController {

    private final AudienceTypeService audienceTypeService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/{id}")
    @Operation(summary = "Получение типа публики по id")
    public AudienceTypeWithoutBondsDto getById(@PathVariable @Parameter(description = "Идентификатор типа публики") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.GET, AUDIENCE_TYPE_ENDPOINT, id));
        AudienceTypeWithoutBondsDto returnDto = audienceTypeService.findById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, AUDIENCE_TYPE_ENDPOINT+id, returnDto));
        return returnDto;
    }

    @GetMapping
    @Operation(summary = "Получение всех типов публики")
    public List<AudienceTypeWithoutBondsDto> getAll(){
        log.info(String.format("%s: %s", HttpMethod.GET, AUDIENCE_TYPE_ENDPOINT));
        List<AudienceTypeWithoutBondsDto> returnListDto = audienceTypeService.findAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, AUDIENCE_TYPE_ENDPOINT, returnListDto));
        return returnListDto;
    }

    @PostMapping
    @Operation(summary = "Добавление нового типа публики")
    public AudienceTypeWithoutBondsDto create(@RequestBody AudienceTypeWithoutBondsDto audienceTypeWithoutBondsDto){
        log.info(String.format("%s: %s", HttpMethod.POST, AUDIENCE_TYPE_ENDPOINT));
        AudienceTypeWithoutBondsDto returnDto = audienceTypeService.create(audienceTypeWithoutBondsDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.POST, AUDIENCE_TYPE_ENDPOINT, returnDto));
        return returnDto;
    }

    @PutMapping
    @Operation(summary = "Изменение имеющегося типа публики")
    public AudienceTypeWithoutBondsDto update(@RequestBody AudienceTypeWithoutBondsDto audienceTypeWithoutBondsDto){
        log.info(String.format("%s: %s", HttpMethod.PUT, AUDIENCE_TYPE_ENDPOINT));
        AudienceTypeWithoutBondsDto returnDto = audienceTypeService.update(audienceTypeWithoutBondsDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.PUT, AUDIENCE_TYPE_ENDPOINT, returnDto));
        return returnDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление типа публики по id")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор типа публики") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.DELETE, AUDIENCE_TYPE_ENDPOINT, id));
        audienceTypeService.deleteById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, AUDIENCE_TYPE_ENDPOINT+id, ""));
    }

    @DeleteMapping
    @Operation(summary = "Удаление всех типов публики")
    public void deleteAll(){
        log.info(String.format("%s: %s", HttpMethod.DELETE, AUDIENCE_TYPE_ENDPOINT));
        audienceTypeService.deleteAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, AUDIENCE_TYPE_ENDPOINT, ""));
    }

}

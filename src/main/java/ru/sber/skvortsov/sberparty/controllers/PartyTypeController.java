package ru.sber.skvortsov.sberparty.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import ru.sber.skvortsov.sberparty.dto.PartyTypeOnlyIdDto;
import ru.sber.skvortsov.sberparty.dto.PartyTypeWithoutBondsDto;
import ru.sber.skvortsov.sberparty.services.PartyTypeService;
import ru.sber.skvortsov.sberparty.utils.KafkaMessage;
import ru.sber.skvortsov.sberparty.utils.KafkaMessageProducer;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.Endpoints.PARTY_TYPE_ENDPOINT;

@RestController
@RequestMapping(PARTY_TYPE_ENDPOINT)
@AllArgsConstructor
@Log4j2
@Tag(name = "Типы праздничных мероприятий", description = "Работа с сущностью 'тип праздничного мероприятия': получение, создание, изменение, удаление")
public class PartyTypeController {

    private final PartyTypeService partyTypeService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/{id}")
    @Operation(summary = "Получение типа мероприятия по id")
    public PartyTypeWithoutBondsDto getById(@PathVariable @Parameter(description = "Идентификатор типа мероприятия") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.GET, PARTY_TYPE_ENDPOINT, id));
        PartyTypeWithoutBondsDto returnDto = partyTypeService.findById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, PARTY_TYPE_ENDPOINT+id, returnDto));
        return returnDto;
    }

    @GetMapping
    @Operation(summary = "Получение всех типов мероприятий")
    public List<PartyTypeWithoutBondsDto> getAll(){
        log.info(String.format("%s: %s", HttpMethod.GET, PARTY_TYPE_ENDPOINT));
        List<PartyTypeWithoutBondsDto> returnListDto = partyTypeService.findAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, PARTY_TYPE_ENDPOINT, returnListDto));
        return returnListDto;
    }

    @PostMapping
    @Operation(summary = "Добавление нового типа мероприятия")
    public PartyTypeWithoutBondsDto create(@RequestBody PartyTypeWithoutBondsDto partyTypeWithoutBondsDto){
        log.info(String.format("%s: %s", HttpMethod.POST, PARTY_TYPE_ENDPOINT));
        PartyTypeWithoutBondsDto returnDto = partyTypeService.create(partyTypeWithoutBondsDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.POST, PARTY_TYPE_ENDPOINT, returnDto));
        return returnDto;
    }

    @PutMapping
    @Operation(summary = "Изменение имеющегося типа мероприятия")
    public PartyTypeWithoutBondsDto update(@RequestBody PartyTypeWithoutBondsDto partyTypeWithoutBondsDto){
        log.info(String.format("%s: %s", HttpMethod.PUT, PARTY_TYPE_ENDPOINT));
        PartyTypeWithoutBondsDto returnDto = partyTypeService.update(partyTypeWithoutBondsDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.PUT, PARTY_TYPE_ENDPOINT, returnDto));
        return returnDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление типа мероприятия по id")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор типа мероприяти") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.DELETE, PARTY_TYPE_ENDPOINT, id));
        partyTypeService.deleteById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, PARTY_TYPE_ENDPOINT+id, ""));
    }

    @DeleteMapping
    @Operation(summary = "Удаление всех типов мероприятий")
    public void deleteAll(){
        log.info(String.format("%s: %s", HttpMethod.DELETE, PARTY_TYPE_ENDPOINT));
        partyTypeService.deleteAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, PARTY_TYPE_ENDPOINT, ""));
    }
}

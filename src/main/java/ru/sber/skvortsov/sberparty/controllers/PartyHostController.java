package ru.sber.skvortsov.sberparty.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import ru.sber.skvortsov.sberparty.dto.PartyHostDto;
import ru.sber.skvortsov.sberparty.dto.PartyHostPostPutDto;
import ru.sber.skvortsov.sberparty.services.PartyHostService;
import ru.sber.skvortsov.sberparty.utils.KafkaMessage;
import ru.sber.skvortsov.sberparty.utils.KafkaMessageProducer;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.Endpoints.PARTY_HOST_ENDPOINT;

@RestController
@RequestMapping(PARTY_HOST_ENDPOINT)
@AllArgsConstructor
@Log4j2
@Tag(name = "Ведущие праздничных мероприятий", description = "Работа с сущностью 'ведущий праздничного мероприятия': получение, создание, изменение, удаление")
public class PartyHostController {

    private final PartyHostService partyHostService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/{id}")
    @Operation(summary = "Получение ведущего по id")
    public PartyHostDto getById(@PathVariable @Parameter(description = "Идентификатор ведущего") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.GET, PARTY_HOST_ENDPOINT, id));
        PartyHostDto returnDto = partyHostService.findById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, PARTY_HOST_ENDPOINT+id, returnDto));
        return returnDto;
    }

    @GetMapping
    @Operation(summary = "Получение всех имеющихся ведущих")
    public List<PartyHostDto> getAll(){
        log.info(String.format("%s: %s", HttpMethod.GET, PARTY_HOST_ENDPOINT));
        List<PartyHostDto> returnListDto = partyHostService.findAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, PARTY_HOST_ENDPOINT, returnListDto));
        return returnListDto;
    }

    @PostMapping
    @Operation(summary = "Добавление нового ведущего")
    public PartyHostPostPutDto create(@RequestBody PartyHostPostPutDto partyHostPostPutDto){
        log.info(String.format("%s: %s", HttpMethod.POST, PARTY_HOST_ENDPOINT));
        PartyHostPostPutDto returnDto = partyHostService.create(partyHostPostPutDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.POST, PARTY_HOST_ENDPOINT, returnDto));
        return returnDto;
    }

    @PutMapping
    @Operation(summary = "Изменение имеющегося ведущего")
    public PartyHostPostPutDto update(@RequestBody PartyHostPostPutDto partyHostPostPutDto){
        log.info(String.format("%s: %s", HttpMethod.PUT, PARTY_HOST_ENDPOINT));
        PartyHostPostPutDto returnDto = partyHostService.update(partyHostPostPutDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.PUT, PARTY_HOST_ENDPOINT, returnDto));
        return returnDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ведущего по id")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор ведущего") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.DELETE, PARTY_HOST_ENDPOINT, id));
        partyHostService.deleteById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, PARTY_HOST_ENDPOINT+id, ""));
    }

    @DeleteMapping
    @Operation(summary = "Удаление всех ведущих")
    public void deleteAll(){
        log.info(String.format("%s: %s", HttpMethod.DELETE, PARTY_HOST_ENDPOINT));
        partyHostService.deleteAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, PARTY_HOST_ENDPOINT, ""));
    }

}

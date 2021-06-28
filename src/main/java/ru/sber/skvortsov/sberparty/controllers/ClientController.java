package ru.sber.skvortsov.sberparty.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import ru.sber.skvortsov.sberparty.dto.ClientDto;
import ru.sber.skvortsov.sberparty.services.ClientService;
import ru.sber.skvortsov.sberparty.utils.KafkaMessage;
import ru.sber.skvortsov.sberparty.utils.KafkaMessageProducer;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.Endpoints.CLIENT_ENDPOINT;

@RestController
@RequestMapping(CLIENT_ENDPOINT)
@AllArgsConstructor
@Log4j2
@Tag(name = "Клиенты", description = "Работа с сущностью 'клиент': получение, создание, изменение, удаление")
public class ClientController {

    private final ClientService clientService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/{id}")
    @Operation(summary = "Получение клиента по id")
    public ClientDto getById(@PathVariable @Parameter(description = "Идентификатор клиента") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.GET, CLIENT_ENDPOINT, id));
        ClientDto returnDto = clientService.findById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, CLIENT_ENDPOINT+id, returnDto));
        return returnDto;
    }

    @GetMapping
    @Operation(summary = "Получение всех клиентов")
    public List<ClientDto> getAll(){
        log.info(String.format("%s: %s", HttpMethod.GET, CLIENT_ENDPOINT));
        List<ClientDto> returnListDto = clientService.findAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.GET, CLIENT_ENDPOINT, returnListDto));
        return returnListDto;
    }

    @PostMapping
    @Operation(summary = "Добавление нового клиента")
    public ClientDto create(@RequestBody ClientDto clientDto){
        log.info(String.format("%s: %s", HttpMethod.POST, CLIENT_ENDPOINT));
        ClientDto returnDto = clientService.create(clientDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.POST, CLIENT_ENDPOINT, returnDto));
        return returnDto;
    }

    @PutMapping
    @Operation(summary = "Изменение имеющегося клиента")
    public ClientDto update(@RequestBody ClientDto clientDto){
        log.info(String.format("%s: %s", HttpMethod.PUT, CLIENT_ENDPOINT));
        ClientDto returnDto = clientService.update(clientDto);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.PUT, CLIENT_ENDPOINT, returnDto));
        return returnDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление клиента по id")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор клиента") Long id){
        log.info(String.format("%s: %s/%d", HttpMethod.DELETE, CLIENT_ENDPOINT, id));
        clientService.deleteById(id);
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, CLIENT_ENDPOINT+id, ""));
    }

    @DeleteMapping
    @Operation(summary = "Удаление всех клиентов")
    public void deleteAll(){
        log.info(String.format("%s: %s", HttpMethod.DELETE, CLIENT_ENDPOINT));
        clientService.deleteAll();
        kafkaMessageProducer.send(new KafkaMessage<>(HttpMethod.DELETE, CLIENT_ENDPOINT, ""));
    }

}

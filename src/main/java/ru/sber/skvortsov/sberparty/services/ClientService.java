package ru.sber.skvortsov.sberparty.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sber.skvortsov.sberparty.dao.ClientRepository;
import ru.sber.skvortsov.sberparty.dto.ClientDto;
import ru.sber.skvortsov.sberparty.entities.Client;
import ru.sber.skvortsov.sberparty.exception.NotFoundException;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.UtilMethods.mapList;

@Service
@AllArgsConstructor
@Log4j2
public class ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public ClientDto findById(Long id){
        log.info("findById({})", id);
        return modelMapper.map(clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Element not found with id : " + id)), ClientDto.class);
    }

    public List<ClientDto> findAll(){
        log.info("findAll()");
        return mapList(clientRepository.findAll(), ClientDto.class, modelMapper);
    }

    public ClientDto create(ClientDto clientDto){
        log.info("create(ClientDto clientDto)");
        clientDto.setId(null);
        return update(clientDto);
    }

    public ClientDto update(ClientDto clientDto){
        log.info("update(ClientDto clientDto)");
        return modelMapper.map(clientRepository.save(modelMapper.map(clientDto, Client.class)), ClientDto.class);
    }

    public void deleteById(Long id){
        log.info("deleteById({})", id);
        clientRepository.deleteById(id);
    }

    public void deleteAll(){
        log.info("deleteAll()");
        clientRepository.deleteAll();
    }
}

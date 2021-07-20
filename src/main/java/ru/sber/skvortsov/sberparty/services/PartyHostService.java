package ru.sber.skvortsov.sberparty.services;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sber.skvortsov.sberparty.dao.PartyHostRepository;
import ru.sber.skvortsov.sberparty.dto.PartyHostDto;
import ru.sber.skvortsov.sberparty.dto.PartyHostPostPutDto;
import ru.sber.skvortsov.sberparty.entities.PartyHost;
import ru.sber.skvortsov.sberparty.exception.NotFoundException;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.UtilMethods.mapList;

@Service
@AllArgsConstructor
@Log4j2
public class PartyHostService {

    private final PartyHostRepository partyHostRepository;
    private final ModelMapper modelMapper;

    public PartyHostDto findById(Long id){
        log.info("findById({})", id);
        return modelMapper.map(partyHostRepository.findById(id).orElseThrow(() -> new NotFoundException("Element not found with id : " + id)), PartyHostDto.class);
    }

    public List<PartyHostDto> findAll(){
        log.info("findAll()");
        return mapList(partyHostRepository.findAll(), PartyHostDto.class, modelMapper);
    }

    @Counted(value = "party.host.add.count", description = "Счетчик добавлений ведущих мероприятия")
    @Timed(value = "party.host.add.time", description = "Время, затраченное на добавление ведущего мероприятия")
    public PartyHostPostPutDto create(PartyHostPostPutDto partyHostPostPutDto){
        log.info("create(PartyHostPostPutDto partyHostPostPutDto)");
        partyHostPostPutDto.setId(null);
        return modelMapper.map(partyHostRepository.save(modelMapper.map(partyHostPostPutDto, PartyHost.class)), PartyHostPostPutDto.class);
    }

    @Counted(value = "party.host.upd.count", description = "Счетчик изменений ведущих мероприятия")
    @Timed(value = "party.host.upd.time", description = "Время, затраченное на изменение ведущего мероприятия")
    public PartyHostPostPutDto update(PartyHostPostPutDto partyHostPostPutDto){
        log.info("update(PartyHostPostPutDto partyHostPostPutDto)");
        return modelMapper.map(partyHostRepository.save(modelMapper.map(partyHostPostPutDto, PartyHost.class)),PartyHostPostPutDto.class);
    }

    public void deleteById(Long id){
        log.info("deleteById({})", id);
        partyHostRepository.deleteById(id);
    }

    public void deleteAll(){
        log.info("deleteAll()");
        partyHostRepository.deleteAll();
    }

}

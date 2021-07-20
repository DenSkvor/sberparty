package ru.sber.skvortsov.sberparty.services;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sber.skvortsov.sberparty.dao.PartyTypeRepository;
import ru.sber.skvortsov.sberparty.dto.PartyTypeWithoutBondsDto;
import ru.sber.skvortsov.sberparty.entities.PartyType;
import ru.sber.skvortsov.sberparty.exception.NotFoundException;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.UtilMethods.mapList;

@Service
@AllArgsConstructor
@Log4j2
public class PartyTypeService {

    private final PartyTypeRepository partyTypeRepository;
    private final ModelMapper modelMapper;

    public PartyTypeWithoutBondsDto findById(Long id){
        log.info("findById({})", id);
        return modelMapper.map(partyTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Element not found with id : " + id)), PartyTypeWithoutBondsDto.class);
    }

    public List<PartyTypeWithoutBondsDto> findAll(){
        log.info("findAll()");
        return mapList(partyTypeRepository.findAll(), PartyTypeWithoutBondsDto.class, modelMapper);
    }

    @Counted(value = "party.type.add.count", description = "Счетчик добавлений типов мероприятий")
    @Timed(value = "party.type.add.time", description = "Время, затраченное на добавление типа мероприятия")
    public PartyTypeWithoutBondsDto create(PartyTypeWithoutBondsDto partyTypeWithoutBondsDto){
        log.info("create(PartyTypeWithoutBondsDto partyTypeWithoutBondsDto)");
        partyTypeWithoutBondsDto.setId(null);
        return update(partyTypeWithoutBondsDto);
    }

    @Counted(value = "party.type.upd.count", description = "Счетчик изменений типов мероприятий")
    @Timed(value = "party.type.upd.time", description = "Время, затраченное на изменение типа мероприятия")
    public PartyTypeWithoutBondsDto update(PartyTypeWithoutBondsDto partyTypeWithoutBondsDto){
        log.info("create(PartyTypeWithoutBondsDto partyTypeWithoutBondsDto)");
        return modelMapper.map(partyTypeRepository.save(modelMapper.map(partyTypeWithoutBondsDto, PartyType.class)), PartyTypeWithoutBondsDto.class);
    }

    public void deleteById(Long id){
        log.info("deleteById({})", id);
        partyTypeRepository.deleteById(id);
    }

    public void deleteAll(){
        log.info("deleteAll()");
        partyTypeRepository.deleteAll();
    }
}

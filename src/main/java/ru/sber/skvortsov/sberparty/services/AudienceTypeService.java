package ru.sber.skvortsov.sberparty.services;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sber.skvortsov.sberparty.dao.AudienceTypeRepository;
import ru.sber.skvortsov.sberparty.dto.AudienceTypeWithoutBondsDto;
import ru.sber.skvortsov.sberparty.entities.AudienceType;
import ru.sber.skvortsov.sberparty.exception.NotFoundException;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.UtilMethods.mapList;


@Service
@AllArgsConstructor
@Log4j2
public class AudienceTypeService {

    private final AudienceTypeRepository audienceTypeRepository;
    private final ModelMapper modelMapper;

    public AudienceTypeWithoutBondsDto findById(Long id){
        log.info("findById({})", id);
        return modelMapper.map(audienceTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Element not found with id : " + id)), AudienceTypeWithoutBondsDto.class);
    }

    public List<AudienceTypeWithoutBondsDto> findAll(){
        log.info("findAll()");
        return mapList(audienceTypeRepository.findAll(), AudienceTypeWithoutBondsDto.class, modelMapper);
    }

    @Counted(value = "audience.type.add.count", description = "Счетчик добавлений типов публики")
    @Timed(value = "audience.type.add.time", description = "Время, затраченное на добавление типа публики")
    public AudienceTypeWithoutBondsDto create(AudienceTypeWithoutBondsDto audienceTypeWithoutBondsDto){
        log.info("create(AudienceTypeWithoutBondsDto audienceTypeWithoutBondsDto)");
        audienceTypeWithoutBondsDto.setId(null);
        return update(audienceTypeWithoutBondsDto);
    }

    @Counted(value = "audience.type.upd.count", description = "Счетчик изменений типов публики")
    @Timed(value = "audience.type.upd.time", description = "Время, затраченное на изменение типа публики")
    public AudienceTypeWithoutBondsDto update(AudienceTypeWithoutBondsDto audienceTypeWithoutBondsDto){
        log.info("update(AudienceTypeWithoutBondsDto audienceTypeWithoutBondsDto)");
        return modelMapper.map(audienceTypeRepository.save(modelMapper.map(audienceTypeWithoutBondsDto, AudienceType.class)), AudienceTypeWithoutBondsDto.class);
    }

    public void deleteById(Long id){
        log.info("deleteById({})", id);
        audienceTypeRepository.deleteById(id);
    }

    public void deleteAll(){
        log.info("deleteAll()");
        audienceTypeRepository.deleteAll();
    }
}

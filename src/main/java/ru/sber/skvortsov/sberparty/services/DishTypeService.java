package ru.sber.skvortsov.sberparty.services;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sber.skvortsov.sberparty.dao.DishTypeRepository;
import ru.sber.skvortsov.sberparty.dto.DishTypeWithoutDishesDto;
import ru.sber.skvortsov.sberparty.entities.DishType;
import ru.sber.skvortsov.sberparty.exception.NotFoundException;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.UtilMethods.mapList;

@Service
@AllArgsConstructor
@Log4j2
public class DishTypeService {

    private final DishTypeRepository dishTypeRepository;
    private final ModelMapper modelMapper;

    public DishTypeWithoutDishesDto findById(Long id){
        log.info("findById({})", id);
        return modelMapper.map(dishTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Element not found with id : " + id)), DishTypeWithoutDishesDto.class);
    }

    public List<DishTypeWithoutDishesDto> findAll(){
        log.info("findAll()");
        return mapList(dishTypeRepository.findAll(), DishTypeWithoutDishesDto.class, modelMapper);
    }

    @Counted(value = "dish.type.add.count", description = "Счетчик добавлений типов блюд")
    @Timed(value = "dish.type.add.time", description = "Время, затраченное на добавление типа блюда")
    public DishTypeWithoutDishesDto create(DishTypeWithoutDishesDto dishTypeWithoutDishesDto){
        log.info("create(DishTypeWithoutDishesDto dishTypeWithoutDishesDto)");
        dishTypeWithoutDishesDto.setId(null);
        return update(dishTypeWithoutDishesDto);
    }

    @Counted(value = "dish.type.upd.count", description = "Счетчик изменений типов блюд")
    @Timed(value = "dish.type.upd.time", description = "Время, затраченное на изменение типа блюда")
    public DishTypeWithoutDishesDto update(DishTypeWithoutDishesDto dishTypeWithoutDishesDto){
        log.info("update(DishTypeWithoutDishesDto dishTypeWithoutDishesDto)");
        return modelMapper.map(dishTypeRepository.save(modelMapper.map(dishTypeWithoutDishesDto, DishType.class)), DishTypeWithoutDishesDto.class);
    }

    public void deleteById(Long id){
        log.info("deleteById({})", id);
        dishTypeRepository.deleteById(id);
    }

    public void deleteAll(){
        log.info("deleteAll()");
        dishTypeRepository.deleteAll();
    }
}

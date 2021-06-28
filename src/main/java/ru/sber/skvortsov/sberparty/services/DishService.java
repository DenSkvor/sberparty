package ru.sber.skvortsov.sberparty.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sber.skvortsov.sberparty.dao.DishRepository;
import ru.sber.skvortsov.sberparty.dto.DishDto;
import ru.sber.skvortsov.sberparty.dto.DishPostPutDto;
import ru.sber.skvortsov.sberparty.entities.Dish;
import ru.sber.skvortsov.sberparty.exception.NotFoundException;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.UtilMethods.mapList;

@Service
@AllArgsConstructor
@Log4j2
public class DishService {

    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;

    public DishDto findById(Long id){
        log.info("findById({})", id);
        return modelMapper.map(dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Element not found with id : " + id)), DishDto.class);
    }

    public List<DishDto> findAll(){
        log.info("findAll()");
        return mapList(dishRepository.findAll(), DishDto.class, modelMapper);
    }

    public DishPostPutDto create(DishPostPutDto dishPostPutDto){
        log.info("create(DishPostPutDto dishPostPutDto)");
        dishPostPutDto.setId(null);
        return update(dishPostPutDto);
    }

    public DishPostPutDto update(DishPostPutDto dishPostPutDto){
        log.info("update(DishPostPutDto dishPostPutDto)");
        return modelMapper.map(dishRepository.save(modelMapper.map(dishPostPutDto, Dish.class)), DishPostPutDto.class);
    }

    public void deleteById(Long id){
        log.info("deleteById({})", id);
        dishRepository.deleteById(id);
    }

    public void deleteAll(){
        log.info("deleteAll()");
        dishRepository.deleteAll();
    }
}

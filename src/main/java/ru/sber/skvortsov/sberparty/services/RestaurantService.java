package ru.sber.skvortsov.sberparty.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sber.skvortsov.sberparty.dao.RestaurantRepository;
import ru.sber.skvortsov.sberparty.dto.RestaurantDto;
import ru.sber.skvortsov.sberparty.dto.RestaurantWithoutBondsDto;
import ru.sber.skvortsov.sberparty.entities.Restaurant;
import ru.sber.skvortsov.sberparty.exception.NotFoundException;

import java.util.HashMap;
import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.UtilMethods.*;

@Service
@AllArgsConstructor
@Log4j2
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;


    public RestaurantDto findById(Long id){
        log.info("findById({})", id);
        return modelMapper.map(restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Element not found with id : " + id)), RestaurantDto.class);
    }

    public List<RestaurantDto> findAll(){
        log.info("findAll()");
        return mapList(restaurantRepository.findAll(), RestaurantDto.class, modelMapper);
    }

    public RestaurantWithoutBondsDto create(RestaurantWithoutBondsDto restaurantWithoutBondsDto){
        log.info("create(RestaurantWithoutBondsDto restaurantWithoutBondsDto)");
        restaurantWithoutBondsDto.setId(null);
        return update(restaurantWithoutBondsDto);
    }

    public RestaurantWithoutBondsDto update(RestaurantWithoutBondsDto restaurantWithoutBondsDto){
        log.info("update(RestaurantWithoutBondsDto restaurantWithoutBondsDto)");
        return modelMapper.map(restaurantRepository.save(modelMapper.map(restaurantWithoutBondsDto, Restaurant.class)), RestaurantWithoutBondsDto.class);
    }

    public void deleteById(Long id){
        log.info("deleteById({})", id);
        restaurantRepository.deleteById(id);
    }

    public void deleteAll(){
        log.info("deleteAll()");
        restaurantRepository.deleteAll();
    }

}

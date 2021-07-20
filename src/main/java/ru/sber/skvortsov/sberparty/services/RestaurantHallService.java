package ru.sber.skvortsov.sberparty.services;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sber.skvortsov.sberparty.dao.RestaurantHallRepository;
import ru.sber.skvortsov.sberparty.dto.RestaurantHallDto;
import ru.sber.skvortsov.sberparty.dto.RestaurantHallPostPutDto;
import ru.sber.skvortsov.sberparty.entities.RestaurantHall;
import ru.sber.skvortsov.sberparty.exception.NotFoundException;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.UtilMethods.mapList;

@Service
@AllArgsConstructor
@Log4j2
public class RestaurantHallService {

    private final RestaurantHallRepository restaurantHallRepository;
    private final ModelMapper modelMapper;

    public RestaurantHallDto findById(Long id){
        log.info("findById({})", id);
        return modelMapper.map(restaurantHallRepository.findById(id).orElseThrow(() -> new NotFoundException("Element not found with id : " + id)), RestaurantHallDto.class);
    }

    public List<RestaurantHallDto> findAll(){
        log.info("findAll()");
        return mapList(restaurantHallRepository.findAll(), RestaurantHallDto.class, modelMapper);
    }

    @Counted(value = "restaurant.hall.add.count", description = "Счетчик добавлений залов ресторанов")
    @Timed(value = "restaurant.hall.add.time", description = "Время, затраченное на добавление зала ресторана")
    public RestaurantHallPostPutDto create(RestaurantHallPostPutDto restaurantHallPostPutDto){
        log.info("create(RestaurantHallPostPutDto restaurantHallPostPutDto)");
        restaurantHallPostPutDto.setId(null);
        return update(restaurantHallPostPutDto);
    }

    @Counted(value = "restaurant.hall.upd.count", description = "Счетчик изменений залов ресторанов")
    @Timed(value = "restaurant.hall.upd.time", description = "Время, затраченное на изменение зала ресторана")
    public RestaurantHallPostPutDto update(RestaurantHallPostPutDto restaurantHallPostPutDto){
        log.info("update(RestaurantHallPostPutDto restaurantHallPostPutDto)");
        return modelMapper.map(restaurantHallRepository.save(modelMapper.map(restaurantHallPostPutDto, RestaurantHall.class)), RestaurantHallPostPutDto.class);
    }

    public void deleteById(Long id){
        log.info("deleteById({})", id);
        restaurantHallRepository.deleteById(id);
    }

    public void deleteAll(){
        log.info("deleteAll()");
        restaurantHallRepository.deleteAll();
    }

}

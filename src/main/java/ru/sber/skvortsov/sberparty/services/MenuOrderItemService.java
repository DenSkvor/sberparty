package ru.sber.skvortsov.sberparty.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sber.skvortsov.sberparty.dao.MenuOrderItemRepository;
import ru.sber.skvortsov.sberparty.dto.MenuOrderItemDto;
import ru.sber.skvortsov.sberparty.exception.NotFoundException;

import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.UtilMethods.mapList;

@Service
@AllArgsConstructor
@Log4j2
public class MenuOrderItemService {

    private final MenuOrderItemRepository menuOrderItemRepository;
    private final ModelMapper modelMapper;

    public MenuOrderItemDto findById(Long id){
        log.info("findById({})", id);
        return modelMapper.map(menuOrderItemRepository.findById(id).orElseThrow(() -> new NotFoundException("Element not found with id : " + id)), MenuOrderItemDto.class);
    }

    public List<MenuOrderItemDto> findAll(){
        log.info("findAll()");
        return mapList(menuOrderItemRepository.findAll(), MenuOrderItemDto.class, modelMapper);
    }
}

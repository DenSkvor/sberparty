package ru.sber.skvortsov.sberparty.services;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sber.skvortsov.sberparty.dao.OrderRepository;
import ru.sber.skvortsov.sberparty.dto.*;
import ru.sber.skvortsov.sberparty.entities.MenuOrderItem;
import ru.sber.skvortsov.sberparty.entities.Order;
import ru.sber.skvortsov.sberparty.exception.NotFoundException;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static ru.sber.skvortsov.sberparty.utils.UtilMethods.mapList;

@Service
@AllArgsConstructor
@Log4j2
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishService dishService;
    private final PartyHostService partyHostService;
    private final RestaurantHallService restaurantHallService;
    private final ModelMapper modelMapper;

    @PostConstruct
    private void setupMapper() {
        modelMapper.createTypeMap(Order.class, OrderPostDto.class).addMappings(m -> m.skip(OrderPostDto::setMenuOrderItems));
    }


    public OrderDto findById(Long id) {
        log.info("findById({})", id);
        return modelMapper.map(orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Element not found with id : " + id)), OrderDto.class);
    }

    public List<OrderDto> findAll() {
        log.info("findAll()");
        return mapList(orderRepository.findAll(), OrderDto.class, modelMapper);
    }

    @Transactional
    @Counted(value = "order.add.count", description = "Счетчик добавления заказов")
    @Timed(value = "order.add.time", description = "Время, затраченное на формирование заказа")
    public OrderPostDto create(OrderPostDto orderPostDto) {
        log.info("create(OrderPostDto orderPostDto)");
        orderPostDto.setId(null);
        Order order = modelMapper.map(orderPostDto, Order.class);
        BigDecimal totalOrderCost = BigDecimal.valueOf(0).setScale(2, RoundingMode.DOWN);
        for (MenuOrderItem moi : order.getMenuOrderItems()) {
            moi.setOrder(order);
            BigDecimal totalOrderItemCost = dishService.findById(moi.getDish().getId()).getPrice().multiply(new BigDecimal(moi.getCount()));
            moi.setTotalCost(totalOrderItemCost.setScale(2, RoundingMode.DOWN));
            totalOrderCost = totalOrderCost.add(totalOrderItemCost);
        }
        totalOrderCost = totalOrderCost.add(partyHostService.findById(orderPostDto.getPartyHost().getId()).getPrice());
        totalOrderCost = totalOrderCost.add(restaurantHallService.findById(orderPostDto.getRestaurantHall().getId()).getPrice());
        order.setTotalCost(totalOrderCost);
        Order returnOrder = orderRepository.save(order);
        OrderPostDto returnOrderPostDto = modelMapper.map(returnOrder, OrderPostDto.class);
        List<MenuOrderItem> returnMenuOrderItems = returnOrder.getMenuOrderItems();
        List<MenuOrderItemPostDto> menuOrderItemsPostDto = mapList(returnMenuOrderItems, MenuOrderItemPostDto.class, modelMapper);
        returnOrderPostDto.setMenuOrderItems(menuOrderItemsPostDto);
        return returnOrderPostDto;
    }

    public void deleteById(Long id) {
        log.info("deleteById({})", id);
        orderRepository.deleteById(id);
    }

    public void deleteAll() {
        log.info("deleteAll()");
        orderRepository.deleteAll();
    }
}

package ru.sber.skvortsov.sberparty.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UtilMethods {

    public static <T> List<T> mapList(List<?> list, Class<T> clazz, ModelMapper mm){
        return list.stream().map(e -> mm.map(e, clazz)).collect(Collectors.toList());
    }

}

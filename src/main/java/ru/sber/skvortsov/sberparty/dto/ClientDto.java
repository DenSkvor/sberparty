package ru.sber.skvortsov.sberparty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientDto {

    private Long id;
    private String firstname;
    private String lastname;
    private List<OrderDto> orders;
}

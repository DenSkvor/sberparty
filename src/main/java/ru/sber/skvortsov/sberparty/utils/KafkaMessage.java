package ru.sber.skvortsov.sberparty.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

@NoArgsConstructor
@Data
public class KafkaMessage <V>{
    private HttpMethod httpMethodType;
    private String path;
    private V value;

    public KafkaMessage(HttpMethod httpMethodType, String path, V value) {
        this.httpMethodType = httpMethodType;
        this.path = path;
        this.value = value;
    }
}

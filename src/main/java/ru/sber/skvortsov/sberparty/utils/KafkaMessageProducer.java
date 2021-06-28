package ru.sber.skvortsov.sberparty.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.sber.skvortsov.sberparty.exception.InternalErrorException;

@Component
@Log4j2
public class KafkaMessageProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Value(value = "${kafka.main-topic.name}")
    private String topicName;

    public <T> void send(T messageObject){
        log.info("send({} messageObject)", messageObject.getClass());
        kafkaTemplate.send(topicName, convertToJson(messageObject));
    }

    public <T> String convertToJson(T messageObject){
        log.info("convertToJson({} messageObject)", messageObject.getClass());
        try {
            return objectMapper.writeValueAsString(messageObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new InternalErrorException("Internal error.");
        }
    }






}

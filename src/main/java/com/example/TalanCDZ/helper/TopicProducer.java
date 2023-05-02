package com.example.TalanCDZ.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class TopicProducer {

    private final NewTopic newAttributesTopic;


    // Serialize the document object to JSON string
    ObjectMapper objectMapper = new ObjectMapper();

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendNewAttributes(List<String> newAttributes) {
        newAttributes.forEach(newAttribute -> {
            kafkaTemplate.send(newAttributesTopic.name(), newAttribute);
        });

    }




}

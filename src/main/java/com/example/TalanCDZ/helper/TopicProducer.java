package com.example.TalanCDZ.helper;

import com.example.TalanCDZ.domain.AdditionalAttributesRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@Slf4j
@RequiredArgsConstructor
public class TopicProducer {

    private final NewTopic newAttributesTopic;

    private final NewTopic synchronisationTopic;


    // Serialize the document object to JSON string
    ObjectMapper objectMapper = new ObjectMapper();

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendNewAttributes(List<AdditionalAttributesRequest> newAttributes) {
        newAttributes.forEach(newAttribute -> {
            kafkaTemplate.send(newAttributesTopic.name(), newAttribute.toString());
            log.info("send attribute : {}  to OCR", newAttribute);

        });

    }

    public void sendNewDocument(String type, String numero){
        kafkaTemplate.send(synchronisationTopic.name(), type + " " + numero);
        log.info("send document : {}  to OCR", type + " " + numero);

    }




}

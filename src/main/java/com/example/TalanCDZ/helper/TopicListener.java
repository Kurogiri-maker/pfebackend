package com.example.TalanCDZ.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

    @Value("${topic.name.consumer}")
    private String topicName;

    @Autowired
    private NewTopic typageTopic;

    @Autowired
    private NewTopic collecteTopic;

    private String typageMessage;

    private String collectMessage;




    private ObjectMapper objectMapper = new ObjectMapper();


    @KafkaListener(topics = "#{@typageTopic.name}",groupId = "group_id")
    public void processDocumentForTypage(@Payload String payload)  {

    }


    @KafkaListener(topics = "#{@collecteTopic.name}",groupId = "group_id" )
    public void processDocumentForCollect(@Payload String payload) throws JsonProcessingException {


        List<String> attributesList = new ArrayList<>();

        // Parse the JSON string into a JsonNode object
        JsonNode jsonNode = objectMapper.readTree(payload);

        // Extract keys from the JsonNode object
        Iterator<String> fieldNames = jsonNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            attributesList.add(fieldName);
        }

        for (String attribute : attributesList) {
            log.info(attribute);
        }

        collectMessage = payload;

    }

    public String getTypageMessage(){
        return this.typageMessage;
    }

    public String getCollectMessage(){
        return this.collectMessage;
    }





}

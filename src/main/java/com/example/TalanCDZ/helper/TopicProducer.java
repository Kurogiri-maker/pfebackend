package com.example.TalanCDZ.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TopicProducer {

    @Value("${topic.name.producer}")
    private String topicName;

    @Autowired
    private NewTopic typageTopic;

    @Autowired
    private NewTopic collecteTopic;

    // Serialize the document object to JSON string
    ObjectMapper objectMapper = new ObjectMapper();

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void getDocumentType(String document) {
        kafkaTemplate.send(typageTopic.name(), document);
    }

    public void collectDocumentData(String documentJson) {
        log.info("Payload : {}", documentJson);
        String jsonString = "{\"id\":\"12345\",\"siren\":\"56789\",\"refMandat\":\"98765\",\"attribute1\":\"value1\",\"attribute2\":\"value2\",\"attribute3\":\"value3\"}";
        log.info("Payload : {}", jsonString);
        kafkaTemplate.send(collecteTopic.name(), jsonString);
    }

}
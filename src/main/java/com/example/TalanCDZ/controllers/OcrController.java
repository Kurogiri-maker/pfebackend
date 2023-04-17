package com.example.TalanCDZ.controllers;

import com.example.TalanCDZ.domain.KafkaResponse;
import com.example.TalanCDZ.helper.KafkaObservable;
import com.example.TalanCDZ.services.OcrService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Base64;

@CrossOrigin("*")
@RestController
@Slf4j
@AllArgsConstructor
public class OcrController {

    private final OcrService service;
    private final KafkaObservable kafkaObservable = KafkaObservable.getInstance();
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private NewTopic typageTopic;


    @PostMapping("/kafka/type")
    public ResponseEntity<KafkaResponse> uploadFileForTypage(@RequestParam("file") MultipartFile fileResource) {

        byte[] fileContent;
        String message = "Please upload a pdf file!";
        String  responseBody = null;
        KafkaResponse reponse = new KafkaResponse(message, responseBody);
        if (service.hasPDFFormat(fileResource)) {
            try {
                // Read file content as byte array
                fileContent = fileResource.getBytes();
            } catch (IOException e) {
                // Handle exception as needed
                message = "Failed to read file content";
                reponse.setMessage(message);
                return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Convert byte array to Base64 encoded string
            String base64FileContent = Base64.getEncoder().encodeToString(fileContent);

            //send file content to kafka topic
            kafkaTemplate.send("typage", base64FileContent);
            message="File content sent to Kafka topic";
            reponse.setMessage(message);

            /*
            // Subscribe to KafkaObservable for response
             kafkaObservable.getObservable().subscribe(response -> {
                log.info("Response received from KafkaObservable: {}", response);
                //reponse.setResult(response);
                responseBody= new ResponseEntity<>(response,HttpStatus.OK);
            });
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
            */
            ResponseEntity<String> responseEntity = null;
            kafkaObservable.getObservable().subscribe(response -> {
                log.info("Response received from KafkaObservable: {}", response);
                reponse.setResult(response);
                log.info(reponse.getResult());
            });

            return new ResponseEntity<>(reponse,HttpStatus.OK);


        }
        return new ResponseEntity<>(reponse,HttpStatus.BAD_REQUEST);
    }

    @KafkaListener(topics = "test-topic", groupId = "group_id")
    public void consume(String message) {
        // Send message to KafkaObservable
        kafkaObservable.sendMessage(message);
        log.info("Message processed and sent to KafkaObservable: {}", message);
    }
}

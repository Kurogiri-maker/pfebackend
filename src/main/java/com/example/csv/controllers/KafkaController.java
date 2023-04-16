package com.example.csv.controllers;

import com.example.csv.domain.KafkaResponse;
import com.example.csv.helper.TopicListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@AllArgsConstructor
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final WebClient webClient;

    private final TopicListener topicListener;

    private final String kafkaUrl = "http://localhost:8087/";


    //Upload a document to collect its data
    @PostMapping("/kafka/collect")
    public ResponseEntity<String> uploadFileForCollect(@RequestParam("file") MultipartFile fileResource){

        byte[] fileContent;


            try {
                // Read file content as byte array
                fileContent = fileResource.getBytes();
            } catch (IOException e) {
                // Handle exception as needed
                return new ResponseEntity<>("Failed to read file content", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Convert byte array to Base64 encoded string
            String base64FileContent = Base64.getEncoder().encodeToString(fileContent);

            // Make POST request using WebClient
            WebClient.ResponseSpec responseSpec = webClient.post()
                    .uri(kafkaUrl+ "collect")
                    .bodyValue(base64FileContent)
                    .retrieve();

            // Extract response body as String
            String responseBody = responseSpec.bodyToMono(String.class).block();

            // Return response body as the response to the client
            return  new ResponseEntity<>(responseBody,HttpStatus.OK);

    }

    //Upload a document to get its type
    @PostMapping("/kafka/type")
    public ResponseEntity<KafkaResponse> uploadFileForTypage(@RequestParam("file") MultipartFile fileResource) {

        byte[] fileContent;
        String message = "Please upload a pdf file!";
        String responseBody = null;
        KafkaResponse reponse = new KafkaResponse(message, responseBody);
        if (hasPDFFormat(fileResource)) {
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

            kafkaTemplate.send("pdf-type", base64FileContent);
            message="File content sent to Kafka topic";
            reponse.setMessage(message);
            return new ResponseEntity<>(reponse, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @KafkaListener(topics = "pdf-type-result", groupId = "group_id")
    public void consumeResult(String message) {
        // Extract result from message
        String result = message;

        // Create response object with result
        KafkaResponse response = new KafkaResponse("File classification successful", result);

        // Return response to client
        ResponseEntity<KafkaResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        // You can return the ResponseEntity here or store it in a variable and return it later, depending on your use case

        // Log message for debugging purposes
        log.info("Received message from Kafka topic pdf-type-result: {}", result);
    }


    public boolean hasPDFFormat (MultipartFile file){
            // Update the content type to match PDF files
            final String TYPE = "application/pdf";

            if (!TYPE.equals(file.getContentType())) {
                return false;
            }

            return true;
        }


        //Get the content of document
        @GetMapping("/kafka/collect")
        public ResponseEntity<String> getContentOfDocument () {
            // Make GET request using WebClient
            WebClient.ResponseSpec responseSpec = webClient.get()
                    .uri(kafkaUrl + "collect")
                    .retrieve();

            // Extract response body as String
            String responseBody = responseSpec.bodyToMono(String.class).block();

            // Return response body as the response to the client
            return ResponseEntity.ok(responseBody);

        }

        //Get the type of document
        @GetMapping("/kafka/type")
        public ResponseEntity<String> getTypeOfDocument () {
            // Make GET request using WebClient
            WebClient.ResponseSpec responseSpec = webClient.get()
                    .uri(kafkaUrl + "type")
                    .retrieve();

            // Extract response body as String
            String responseBody = responseSpec.bodyToMono(String.class).block();

            // Return response body as the response to the client
            return ResponseEntity.ok(responseBody);

        }

}

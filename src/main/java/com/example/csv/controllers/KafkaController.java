package com.example.csv.controllers;

import com.example.csv.domain.KafkaResponse;
import com.example.csv.helper.TopicListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    private final WebClient webClient;

    private final TopicListener topicListener;


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
                    .uri("http://localhost:8080/collect")
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
        KafkaResponse reponse = new KafkaResponse(message,responseBody);
        if(hasPDFFormat(fileResource)) {
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

            // Make POST request using WebClient
            WebClient.ResponseSpec responseSpec = webClient.post()
                    .uri("http://localhost:8080/type")
                    .bodyValue(base64FileContent)
                    .retrieve();

            // Extract response body as String
            responseBody = responseSpec.bodyToMono(String.class).block();
            if (responseBody != null) {
                // Kafka message processing logic
                String processedResponse = topicListener.getTypageMessage();

                // Return response body as the response to the client
                return new ResponseEntity<>(new KafkaResponse("Uploaded the file successfully",processedResponse), HttpStatus.OK);

            }else{
                // Handle exception as needed
                return new ResponseEntity<>(new KafkaResponse( "Failed to process Kafka message",null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(reponse,HttpStatus.BAD_REQUEST);
    }

    public  boolean hasPDFFormat(MultipartFile file) {
        // Update the content type to match PDF files
        final String TYPE = "application/pdf";

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }


    //Get the content of document
    @GetMapping("/kafka/collect")
    public ResponseEntity<String> getContentOfDocument(){
        // Make GET request using WebClient
        WebClient.ResponseSpec responseSpec = webClient.get()
                .uri("http://localhost:8080/collect")
                .retrieve();

        // Extract response body as String
        String responseBody = responseSpec.bodyToMono(String.class).block();

        // Return response body as the response to the client
        return ResponseEntity.ok(responseBody);

    }

    //Get the type of document
    @GetMapping("/kafka/type")
    public ResponseEntity<String> getTypeOfDocument(){
        // Make GET request using WebClient
        WebClient.ResponseSpec responseSpec = webClient.get()
                .uri("http://localhost:8080/type")
                .retrieve();

        // Extract response body as String
        String responseBody = responseSpec.bodyToMono(String.class).block();

        // Return response body as the response to the client
        return ResponseEntity.ok(responseBody);

    }

}

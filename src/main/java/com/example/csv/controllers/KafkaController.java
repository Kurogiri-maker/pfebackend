package com.example.csv.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Base64;

@RestController
@Slf4j
@AllArgsConstructor
public class KafkaController {



    private final WebClient webClient;

    @PostMapping("/kafka")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile fileResource){
        String jsonString = "file";

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

        // Make GET request using WebClient
        WebClient.ResponseSpec responseSpec = webClient.post()
                .uri("http://localhost:8080/collect")
                //.body(BodyInserters.fromMultipartData("file",fileResource))
                .bodyValue(base64FileContent)
                .retrieve();

        // Extract response body as String
        String responseBody = responseSpec.bodyToMono(String.class).block();


        // Return response body as the response to the client
        return  new ResponseEntity<>(responseBody,HttpStatus.OK);

    }

    @GetMapping("/kafka")
    public ResponseEntity<String> getMessage(){
        // Make GET request using WebClient
        WebClient.ResponseSpec responseSpec = webClient.get()
                .uri("http://localhost:8080/collect")
                .retrieve();

        // Extract response body as String
        String responseBody = responseSpec.bodyToMono(String.class).block();

        // Return response body as the response to the client
        return ResponseEntity.ok(responseBody);

    }

}

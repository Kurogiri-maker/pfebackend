package com.example.csv.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@Slf4j
@AllArgsConstructor
public class KafkaController {



    private final WebClient webClient;

    @PostMapping("/kafka")
    public ResponseEntity<String> uploadFile(){
        String jsonString = "file";

        // Make GET request using WebClient
        WebClient.ResponseSpec responseSpec = webClient.post()
                .uri("http://localhost:8080/collect")
                .bodyValue(jsonString)
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

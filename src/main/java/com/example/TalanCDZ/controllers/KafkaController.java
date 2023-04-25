package com.example.TalanCDZ.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.TalanCDZ.domain.KafkaResponse;
import com.example.TalanCDZ.services.OCRService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin("*")
@RestController
@Slf4j
@AllArgsConstructor
public class KafkaController {


    private final WebClient webClient;

    private final OCRService service;

    //private final TopicListener topicListener;


    //Upload a document to collect its data
    @PostMapping("/kafka/collect")
    public ResponseEntity<String> uploadFileForCollect(@RequestParam("file") MultipartFile file) {

        byte[] fileContent;

        if (hasPDFFormat(file)){
            try {
                // Read file content as byte array
                fileContent = file.getBytes();
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
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);


    }



    //Upload a document to get its type
    @PostMapping("/kafka/type")
    public ResponseEntity<String> uploadFileForTypage(@RequestParam("file") MultipartFile file) {

        byte[] fileContent;
        String message = "Please upload a pdf file!";
        String responseBody = null;
        KafkaResponse reponse = new KafkaResponse(message,responseBody);
        if(hasPDFFormat(file)) {
            try {
                // Read file content as byte array
                fileContent = file.getBytes();
            } catch (IOException e) {
                // Handle exception as needed
                message = "Failed to read file content";
                reponse.setMessage(message);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
            log.info(responseBody);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }

        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    public  boolean hasPDFFormat(MultipartFile file) {
        // Update the content type to match PDF files
        final String TYPE = "application/pdf";

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    @PostMapping("/verify")
    public String verifyCoherence(@RequestBody String doc) throws JsonProcessingException {
        // Create an ObjectMapper object
        ObjectMapper objectMapper = new ObjectMapper();
        // Use the readValue method to convert the JSON string to a Map<String, String>
        Map<String, String> map = objectMapper.readValue(doc, new TypeReference<Map<String, String>>(){});

        // Get the type of document
        String type = map.get("type");
        map.remove("type");
        System.out.println(map);
        // Get the attributes of this class
        List<String> attributes = service.getAttributes(type);

        // Get the attributes of the document
        List<String> keyList = map.keySet().stream().collect(Collectors.toList());
        keyList.forEach(key -> System.out.println(key));

        Map<String,String> map1 = new LinkedHashMap<>();

        for (String key : attributes) {
            map1.put(key,map.get(key));
        }
        System.out.println(map1);
        System.out.println(keyList.containsAll(attributes));
        if(keyList.containsAll(attributes)){
            if (service.searchDocument(type,map1)) {
                return "Le fichier existe";
            }else{
                return "Le fichier n'existe pas. Voulez vous le sauvegardez ?";
            }
        }
        return "Le fichier n'est pas cohérent";

    }

}

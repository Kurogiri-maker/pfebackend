package com.example.TalanCDZ.controllers;

import com.example.TalanCDZ.domain.AdditionalAttributesRequest;
import com.example.TalanCDZ.domain.Response;
import com.example.TalanCDZ.helper.TopicProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.TalanCDZ.domain.KafkaResponse;
import com.example.TalanCDZ.services.OCRService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;


@CrossOrigin("*")
@RestController
@Slf4j
@NoArgsConstructor
@RequestMapping("/api/ocr")
public class OCRController {

    @Autowired
    private WebClient webClient;

    @Autowired
    private OCRService service;

    @Autowired
    private TopicProducer producer;
    //private final TopicListener topicListener;

    @Value("${ocr.port}")
    private String PORT;

    @Value("${ocr.url}")
    private String URL;



    //Upload a document to collect its data
    @PostMapping("/collect")
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
            String ocrUrl = URL+":"+PORT;
            WebClient.ResponseSpec responseSpec = webClient.post()
                    .uri(ocrUrl + "/collect")
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
    @PostMapping("/type")
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

            String ocrUrl = URL+":"+PORT;

            // Make POST request using WebClient
            WebClient.ResponseSpec responseSpec = webClient.post()
                    .uri(ocrUrl+ "/type")
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
    public Response verifyCoherence(@RequestBody String doc) throws JsonProcessingException {

        // Create an ObjectMapper object
        ObjectMapper objectMapper = new ObjectMapper();

        // Use the readValue method to convert the JSON string to a Map<String, String>
        Map<String, String> map = objectMapper.readValue(doc, new TypeReference<Map<String, String>>(){});

        // Get the type of document
        String type = map.get("type");
        map.remove("type");
        // Get the attributes of this class
        List<String> attributes = service.getAttributes(type);

        // Get the attributes of the document
        List<String> keyList = map.keySet().stream().toList();

        // Create a new map for existing attributes
        Map<String,String> legacyAttributes = new LinkedHashMap<>();
        for (String key : attributes) {
            legacyAttributes.put(key,map.get(key));
        }



        // Create a new map for additional attributes


        List<String> additionalAttributesList = new ArrayList<>(keyList);

        additionalAttributesList.removeAll(attributes);
        System.out.println(additionalAttributesList);

        Map<String,String> additionalAttributes = new LinkedHashMap<>();
        for (String key : additionalAttributesList) {
            additionalAttributes.put(key,map.get(key));
        }

        System.out.println(additionalAttributes);




        if(keyList.containsAll(attributes)){
            Long id = service.searchDocument(type,legacyAttributes);
            if (id != null) {
                legacyAttributes.replace("id", String.valueOf(id));
                Response response = new Response();
                response.setType(type);
                response.setLegacyAttributes(legacyAttributes);
                response.setAdditionalAttributes(additionalAttributes);
                response.setMessage("Le fichier existe");
                return response;
            }else{
                Response response = new Response();
                response.setType(type);
                response.setLegacyAttributes(legacyAttributes);
                response.setAdditionalAttributes(additionalAttributes);
                response.setMessage("Le fichier n'existe pas. Voulez vous le sauvegardez ?");
                return response ;
            }
        }
        Response response = new Response();
        response.setMessage("Le fichier n'est pas cohérent");
        return response;

    }

    @PostMapping("/attributes")
    public ResponseEntity<Void> addAttributes(@RequestBody List<AdditionalAttributesRequest> attributes){
        producer.sendNewAttributes(attributes);
        return ResponseEntity.ok().build();
    }

}

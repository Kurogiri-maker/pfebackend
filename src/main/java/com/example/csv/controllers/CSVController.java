package com.example.csv.controllers;

import com.example.csv.domain.ResponseMessage;
import com.example.csv.helper.CSVHelper;
import com.example.csv.services.CSVService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/csv")
@AllArgsConstructor
@Slf4j
public class CSVController {

    private static final String UPLOAD_DIR = "./uploads";
    @Autowired
    private final CSVService fileService;

    // Upload a csv file
    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {

                fileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                log.info(String.valueOf(e));
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


    // Get the columns header
    @CrossOrigin
    @GetMapping("/header")
    public ResponseEntity<List<String>> getColumnsHeader(@RequestParam("file") MultipartFile file) {
        try {
            List<String> columnsHeader = fileService.getColumnsHeader(file.getInputStream());

            if (columnsHeader.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(columnsHeader, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.example.csv.controllers;

import com.example.csv.domain.Contrat;
import com.example.csv.domain.ResponseMessage;
import com.example.csv.domain.Tiers;
import com.example.csv.helper.CSVHelper;
import com.example.csv.services.CSVService;
import com.example.csv.services.ContratService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/csv/contrat")
@AllArgsConstructor
@Slf4j
public class ContratController {

    @Autowired
    private final ContratService fileService;
    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {

                fileService.saveFile(file);

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
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Contrat> save(@RequestBody Contrat contrat){

        if(!(fileService.getContrat(contrat.getId()) == null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Contrat savedContrat = fileService.save(contrat);
        return new ResponseEntity<>(savedContrat,HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/contrats")

    public ResponseEntity<List<Contrat>> getAllContrat() {
        try {
            List<Contrat> contrats = fileService.getAllContrat();

            if (contrats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contrats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Contrat> getContrat(@PathVariable("id") Long id){
        Contrat contrat = fileService.getContrat(id);
        if(contrat.equals(null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(contrat, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteContrat(@PathVariable("id") Long id){
        if(fileService.getContrat(id)== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        fileService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

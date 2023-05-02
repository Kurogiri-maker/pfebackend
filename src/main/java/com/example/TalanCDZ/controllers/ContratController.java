package com.example.TalanCDZ.controllers;

import com.example.TalanCDZ.DTO.ContratDTO;
import com.example.TalanCDZ.domain.Contrat;
import com.example.TalanCDZ.domain.Dossier;
import com.example.TalanCDZ.domain.ResponseMessage;
import com.example.TalanCDZ.helper.CSVHelper;
import com.example.TalanCDZ.services.ContratService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/csv/contrat")
@AllArgsConstructor
@Slf4j
public class ContratController {

    @Autowired
    private final ContratService fileService;

    @GetMapping("/attributes")
    public ResponseEntity<?> getMetadata(){
        Class<?> clazz = Contrat.class;
        Field[] fields = clazz.getDeclaredFields();
        List<String> attributes = new ArrayList<>();
        for (Field field: fields){
            attributes.add(field.getName());
        }
        return new ResponseEntity<>(attributes,HttpStatus.OK);
    }

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

    @PostMapping
    public ResponseEntity<Contrat> save(@RequestBody Contrat contrat){

       /* if(!(fileService.getContrat(contrat.getId()) == null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }*/

        Contrat savedContrat = fileService.save(contrat);
        return new ResponseEntity<>(savedContrat,HttpStatus.CREATED);
    }



    /*@GetMapping
    public ResponseEntity<List<Contrat>> getAllContrat() {
        try {
            List<Contrat> contrats = fileService.getAllContrats();

            if (contrats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contrats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @CrossOrigin
    @GetMapping
    public ResponseEntity<Page<Contrat>> getAllContrats(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy
    )
    {
        Page<Contrat> list = fileService.getAllContrats(page, size, sortBy);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Contrat>> searchDossier(
            @RequestParam(required = false) String searchTerm
    ){
        List<Contrat> contrats = fileService.searchContrat(searchTerm) ;
        return new ResponseEntity<>(contrats,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Contrat> getContrat(@PathVariable("id") Long id){
        Contrat contrat = fileService.getContrat(id);
        if(contrat==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(contrat, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteContrat(@PathVariable("id") Long id){
        if(fileService.getContrat(id)== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        fileService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTiers(@PathVariable("id") Long id ,@RequestBody ContratDTO contratDTO){
        fileService.update(id,contratDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }





}

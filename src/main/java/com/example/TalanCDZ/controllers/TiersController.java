package com.example.TalanCDZ.controllers;

import com.example.TalanCDZ.domain.ResponseMessage;
import com.example.TalanCDZ.domain.Tiers;
import com.example.TalanCDZ.DTO.TiersDTO;
import com.example.TalanCDZ.helper.CSVHelper;
import com.example.TalanCDZ.services.TiersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/csv/tier")
@AllArgsConstructor
@Slf4j
public class TiersController {

    @Autowired
    private final TiersService fileService;


    // Get attributes
    @GetMapping("/attributes")
    public ResponseEntity<?> getMetadata(){
        Class<?> clazz = Tiers.class;
        Field[] fields = clazz.getDeclaredFields();
        List<String> attributes = new ArrayList<>();
        for (Field field: fields){
            attributes.add(field.getName());
        }
        return new ResponseEntity<>(attributes,HttpStatus.OK);
    }

    // Upload a csv file to save all tiers in it
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file)  {
        String message = "Please upload a csv file!";
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


    // Save a tier in the database
    @PostMapping
    public ResponseEntity<Tiers> save(@RequestBody Tiers tiers){
/*
        if(!(fileService.getTiers(tiers.getId()) == null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }*/

        Tiers savedTiers = fileService.save(tiers);
        return new ResponseEntity<>(savedTiers,HttpStatus.CREATED);
    }


    // Get all tiers from database
    /*
    @GetMapping ResponseEntity<List<Tiers>> getAllTiers(){

        try {

            List<Tiers> tiers = fileService.getAllTiers();

            if (tiers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tiers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/




    @GetMapping
    public ResponseEntity<Page<Tiers>> getAllTiers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy
    )
    {
        Page<Tiers> list = fileService.getAllTiers(page, size, sortBy);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    // Get a tier by its id
    @GetMapping("/{id}")
    public ResponseEntity<Tiers> getTiers(@PathVariable("id")Long id){
        Tiers tiers = fileService.getTiers(id);
        if(tiers == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tiers, HttpStatus.OK);
    }


    // Get a tier by its name
    @GetMapping("/search/{nom}")
    public ResponseEntity<List<Tiers>> searchByName(@PathVariable("nom")String nom){
        List<Tiers> tiers = fileService.search(nom);
        if(tiers == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        return new ResponseEntity<>(tiers, HttpStatus.OK);
    }
    //Search filter
    @GetMapping("/search")
    public ResponseEntity<List<Tiers>> searchTiers(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String siren,
            @RequestParam(required = false) String ref_mandat
    ){
        List<Tiers> tiers = fileService.searchTiers(nom,siren,ref_mandat) ;
        return new ResponseEntity<>(tiers,HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Tiers>> searchTiers(
            @RequestParam(required = false) String searchTerm
    ){
        List<Tiers> tiers = fileService.searchTiers(searchTerm) ;
        return new ResponseEntity<>(tiers,HttpStatus.OK);
    }

    // Delete a tier by its id
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteTiers(@PathVariable("id") Long id){
        if(fileService.getTiers(id)== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        fileService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);}

    //Update a tier by its id
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTiers(@PathVariable("id") Long id ,@RequestBody TiersDTO tiersDTO){
        fileService.update(id,tiersDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


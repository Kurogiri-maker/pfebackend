package com.example.csv.controllers;

import com.example.csv.domain.ResponseMessage;
import com.example.csv.domain.Tiers;
import com.example.csv.DTO.TiersDTO;
import com.example.csv.helper.CSVHelper;
import com.example.csv.services.TiersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/csv/tier")
@AllArgsConstructor
@Slf4j
public class TiersController {

    @Autowired
    private final TiersService fileService;
    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file)  {
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
    public ResponseEntity<Tiers> save(@RequestBody Tiers tiers){

        if(!(fileService.getTiers(tiers.getId()) == null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Tiers savedTiers = fileService.save(tiers);
        return new ResponseEntity<>(savedTiers,HttpStatus.CREATED);
    }
    @CrossOrigin
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
    }
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Tiers> getTiers(@PathVariable("id")Long id){
        Tiers tiers = fileService.getTiers(id);
        if(tiers.equals(null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tiers, HttpStatus.OK);
    }
    @CrossOrigin
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteTiers(@PathVariable("id") Long id){
        if(fileService.getTiers(id)== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        fileService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTiers(@PathVariable("id") Long id ,@RequestBody TiersDTO tiersDTO){

        Tiers tiers = fileService.getTiers(id);
        TiersDTO d = new TiersDTO();
        if(tiers == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if(tiersDTO.getNom() == null){
            d.setNom(tiers.getNom());
        }else {
            d.setNom(tiersDTO.getNom());
        }
        if(tiersDTO.getSiren() == null){
            d.setSiren(tiers.getSiren());
        }else {
            d.setSiren(tiersDTO.getSiren());
        }
        if(tiersDTO.getRef_mandat() == null){
            d.setRef_mandat(tiers.getRef_mandat());
        }else {
            d.setRef_mandat(tiersDTO.getRef_mandat());
        }
        fileService.update();
        return new ResponseEntity<>(HttpStatus.OK);


    }



}













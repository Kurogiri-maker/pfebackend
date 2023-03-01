package com.example.csv.controllers;

import com.example.csv.DTO.DossierDTO;
import com.example.csv.domain.*;
import com.example.csv.helper.CSVHelper;
import com.example.csv.services.DossierService;
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
@RequestMapping("/api/csv/dossier")
@AllArgsConstructor
@Slf4j
public class DossierController {

    @Autowired
    private final DossierService fileService;
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
    public ResponseEntity<Dossier> save(@RequestBody Dossier dossier){

        if(!(fileService.getDossier(dossier.getId()) == null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Dossier savedDossier = fileService.save(dossier);
        return new ResponseEntity<>(savedDossier,HttpStatus.CREATED);
    }
    @CrossOrigin
    @GetMapping ResponseEntity<List<Dossier>> getAllDossiers(){
        try {
            List<Dossier> dossiers = fileService.getAllDossiers();

            if (dossiers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(dossiers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Dossier> getDossier(@PathVariable("id") Long id){
        Dossier dossier = fileService.getDossier(id);
        if(dossier.equals(null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dossier, HttpStatus.OK);
    }
    @CrossOrigin
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteDossier(@PathVariable("id") Long id){
        if(fileService.getDossier(id)== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        fileService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateDossier(@PathVariable("id") Long id ,@RequestBody DossierDTO dossierDTO){
        Dossier dossier = fileService.getDossier(id);
        DossierDTO d = new DossierDTO();
        if(dossier == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if(dossierDTO.getDossier_DC() == null){
            d.setDossier_DC(dossier.getDossier_DC());
        }else {
            d.setDossier_DC(dossierDTO.getDossier_DC());
        }
        if(dossierDTO.getListSDC() == null){
            d.setListSDC(dossier.getListSDC());
        }else {
            d.setListSDC(dossierDTO.getListSDC());
        }
        if(dossierDTO.getMontant_du_pres() == null){
            d.setMontant_du_pres(dossier.getMontant_du_pres());
        }else {
            d.setMontant_du_pres(dossierDTO.getMontant_du_pres());
        }
        if(dossierDTO.getN_DPS() == null){
            d.setN_DPS(dossier.getN_DPS());
        }else {
            d.setN_DPS(dossierDTO.getN_DPS());
        }
        fileService.update(id,d.getDossier_DC(),d.getListSDC(),d.getN_DPS(),d.getMontant_du_pres());
        return new ResponseEntity<>(HttpStatus.OK);

    }

}

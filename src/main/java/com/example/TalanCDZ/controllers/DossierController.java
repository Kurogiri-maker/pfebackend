package com.example.TalanCDZ.controllers;

import com.example.TalanCDZ.DTO.DossierDTO;
import com.example.TalanCDZ.domain.*;
import com.example.TalanCDZ.helper.CSVHelper;
import com.example.TalanCDZ.services.AdditionalAttributesDossierService;
import com.example.TalanCDZ.services.DossierService;
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
@RequestMapping("/api/csv/dossier")
@AllArgsConstructor
@Slf4j
public class DossierController {

    @Autowired
    private final DossierService fileService;

    @Autowired
    private final AdditionalAttributesDossierService service;

    @GetMapping("/attributes")
    public ResponseEntity<?> getMetadata(){
        Class<?> clazz = Dossier.class;
        Field[] fields = clazz.getDeclaredFields();
        List<String> attributes = new ArrayList<>();
        for (Field field: fields){
            attributes.add(field.getName());
        }
        attributes.remove(attributes.size()-1);
        attributes.addAll(service.getDistinctAttributeCle());
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
    public ResponseEntity<Dossier> save(@RequestBody Dossier dossier){

        /*if(!(fileService.getDossier(dossier.getId()) == null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }*/

        Dossier savedDossier = fileService.save(dossier);
        return new ResponseEntity<>(savedDossier,HttpStatus.CREATED);
    }

    /*@CrossOrigin
>>>>>>> ahmed
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
    }*/

    @CrossOrigin
    @GetMapping
    public ResponseEntity<Page<Dossier>> getAllDossiers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy
    )
    {
        Page<Dossier> list = fileService.getAllDossiers(page, size, sortBy);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Dossier>> searchDossier(
            @RequestParam(required = false) String dossier_DC,
            @RequestParam(required = false) String listSDC,
            @RequestParam(required = false) String n_DPS,
            @RequestParam(required = false) String montant_du_pres
    ){
        List<Dossier> dossiers = fileService.searchDossiers( dossier_DC,  listSDC,  n_DPS,  montant_du_pres) ;
        return new ResponseEntity<>(dossiers,HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Dossier>> searchDossier(
            @RequestParam(required = false) String searchTerm
    ){
        List<Dossier> dossiers = fileService.searchDossiers(searchTerm) ;
        return new ResponseEntity<>(dossiers,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Dossier> getDossier(@PathVariable("id") Long id){
        Dossier dossier = fileService.getDossier(id);
        if(dossier.equals(null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dossier, HttpStatus.OK);
    }

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
        fileService.update(id,dossierDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}

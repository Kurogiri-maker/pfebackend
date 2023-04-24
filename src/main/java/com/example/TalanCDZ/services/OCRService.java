package com.example.TalanCDZ.services;

import com.example.TalanCDZ.domain.Contrat;
import com.example.TalanCDZ.domain.Dossier;
import com.example.TalanCDZ.domain.Tiers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OCRService {

    private final TiersService tiers;

    private final DossierService dossier;

    private final ContratService contrat;

    // create an object mapper
    ObjectMapper objectMapper = new ObjectMapper();


    public List<String> getAttributes(String classe ){
        Class<?> clazz = null;

        switch (classe) {
            case "Tiers":
                clazz = Tiers.class;
                break;
            case "Contrat":
                clazz = Contrat.class;
                break;
            case "Dossier":
                clazz = Dossier.class;
                break;
            default:
                clazz = null;
                break;
        }

        if(clazz != null){
            Field[] fields = clazz.getDeclaredFields();
            List<String> attributes = new ArrayList<>();
            for (Field field: fields){
                attributes.add(field.getName());
            }
            return attributes;
        }
        return null;

    }

    public boolean searchDocument(String classe,Map<String,String> map) throws JsonProcessingException {


        String numero = map.get("numero");

        String json = objectMapper.writeValueAsString(map);

        switch (classe) {
            case "Tiers":
                // convert JSON string to object
                Tiers t = objectMapper.readValue(json, Tiers.class);
                System.out.println(t);
                List<Tiers> list = tiers.searchTiers(numero);
                Tiers t1 = list.get(0);
                System.out.println(t1);
                return t.equals(t1);

            case "Contrat":
                break;
            case "Dossier":
                // convert JSON string to object
                Dossier d = objectMapper.readValue(json, Dossier.class);
                System.out.println(d);
                List<Dossier> listD = dossier.searchDossiers(numero);
                Dossier d1 = listD.get(0);
                System.out.println(d1);
                return d.equals(d1);
            default:
                return false;
        }

        return false;


    }



}

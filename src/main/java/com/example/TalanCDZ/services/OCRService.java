package com.example.TalanCDZ.services;

import com.example.TalanCDZ.domain.Contrat;
import com.example.TalanCDZ.domain.Dossier;
import com.example.TalanCDZ.domain.Tiers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

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

    public boolean searchDocument(String classe,Map<String,String> map,Map<String,String> map2) throws JsonProcessingException {


        String numero = map.get("numero");

        String json = objectMapper.writeValueAsString(map);

        switch (classe) {
            case "Tiers":
                // convert JSON string to object
                Tiers t = objectMapper.readValue(json, Tiers.class);
                List<Tiers> list = tiers.searchTiers(numero);
                if(list.isEmpty()){
                    break;
                }else{
                    Tiers t1 = list.get(0);
                    Boolean r = t.equals(t1);
                    return r;
                }

            case "Contrat":
                // convert JSON string to object
                Contrat c = objectMapper.readValue(json, Contrat.class);
                List<Contrat> listC = contrat.searchContrat(numero);
                if(listC.isEmpty()){
                    break;
                }else{
                    Contrat c1 = listC.get(0);
                    Boolean r2 = c.equals(c1);
                    return r2;
                }
            case "Dossier":
                // convert JSON string to object
                Dossier d = objectMapper.readValue(json, Dossier.class);
                List<Dossier> listD = dossier.searchDossiers(numero);
                if(listD.isEmpty()){
                    break;
                }else{
                    Dossier d1 = listD.get(0);
                    Boolean r1 = d.equals(d1);
                    return r1;
                }

            default:
                return false;
        }

        return false;


    }




}

package com.example.TalanCDZ.helper;


import com.example.TalanCDZ.domain.*;
import com.example.TalanCDZ.services.ContratService;
import com.example.TalanCDZ.services.DossierService;
import com.example.TalanCDZ.services.TiersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicListener.class);

    @Value("${topic.name.consumer}")
    private String topicName;

    @Autowired
    private NewTopic enrichmentTopic;

    private String typageMessage;

    private String collectMessage;
    @Autowired
    private ContratService contratService;
    @Autowired
    private TiersService tiersService;
    @Autowired
    private DossierService dossierService;



    private ObjectMapper objectMapper = new ObjectMapper();


    /*@KafkaListener(topics = "#{@typageTopic.name}",groupId = "group_id")
    public void processDocumentForTypage(@Payload String payload) throws JsonProcessingException {

        // Parse the JSON string into a JsonNode object
        JsonNode jsonNode = objectMapper.readTree(payload);

        typageMessage = jsonNode.get("type").asText();
        log.info(typageMessage);


    }*/

    @KafkaListener(topics = "#{@enrichmentTopic.name}",groupId = "group_id")
    public void getDocumentProcessed(@Payload String payload) throws JsonProcessingException {

        LOGGER.info("\n" +
                " New attributes values /////////////////////////////////////////////////////////\n" +
                "Payload : "+ payload+"\n" +
                "/////////////////////////////////////////////////////////\n"
                );

        // {"type": "Tiers",
        //"numero": "20",
        //"attributeName": "attribute1",
        //"attributeValue": "CjzsNYO"} Payload example

        AdditionalAttributesResponse dto = objectMapper.readValue(
                payload,
                AdditionalAttributesResponse.class
        );
        switch (dto.getType()) {
            case "Tiers":
                if (tiersService.findByNumero(dto.getNumero()).isPresent()) {
                    Tiers tiers = tiersService.findByNumero(dto.getNumero()).get();
                    Tiers result = new Tiers();
                    result.setId(tiers.getId());
                    result.setNumero(tiers.getNumero());
                    result.setNom(tiers.getNom());
                    result.setSiren(tiers.getSiren());
                    result.setRefMandat(tiers.getRefMandat());
                    result.getAdditionalAttributesSet().add(AdditionalAttributesTiers.builder()
                            .cle(dto.getAttributeName())
                            .valeur(dto.getAttributeValue())
                            .tiers(result)
                            .build());
                    tiersService.save(result);
                    LOGGER.info(String.format("""
                            //////////////
                            Tiers %s updated\s
                             new value %s""", dto.getNumero(), tiersService.findByNumero(dto.getNumero()).get().toString()));
                }
                break;
            case "Contrat":
                LOGGER.info("Contrat");
                if (contratService.findByNumero(dto.getNumero()).isPresent()) {
                    Contrat contrat = contratService.findByNumero(dto.getNumero()).get();
                    Contrat result = new Contrat();
                    result.setId(contrat.getId());
                    result.setNumero(contrat.getNumero());
                    result.setRaisonSocial(contrat.getRaisonSocial());
                    result.setCodeProduit(contrat.getCodeProduit());
                    result.setProduit(contrat.getProduit());
                    result.setPhase(contrat.getPhase());
                    result.setMontantPret(contrat.getMontantPret());
                    result.getAdditionalAttributesSet().add(AdditionalAttributesContrat.builder()
                            .cle(dto.getAttributeName())
                            .valeur(dto.getAttributeValue())
                            .contrat(result)
                            .build());
                    contratService.save(result);
                    LOGGER.info(String.format("""
                            //////////////
                            Contrat %s updated\s
                             new value %s""", dto.getNumero(), contratService.findByNumero(dto.getNumero()).get().toString()));
                }
                break;
            case "Dossier":
                LOGGER.info("Dossier");
                if (dossierService.findByNumero(dto.getNumero()).isPresent()) {
                    Dossier dossier = dossierService.findByNumero(dto.getNumero()).get();
                    Dossier result = new Dossier();
                    result.setId(dossier.getId());
                    result.setNumero(dossier.getNumero());
                    result.setDossierDC(dossier.getDossierDC());
                    result.setListSDC(dossier.getListSDC());
                    result.setN_DPS(dossier.getN_DPS());
                    result.setMontant_du_pres(dossier.getMontant_du_pres());
                    result.getAdditionalAttributesSet().add(AdditionalAttributesDossier.builder()
                            .cle(dto.getAttributeName())
                            .valeur(dto.getAttributeValue())
                            .dossier(result)
                            .build());
                    dossierService.save(result);
                    LOGGER.info(String.format("""
                            //////////////
                            Dossier %s updated\s
                             new value %s""", dto.getNumero(), dossierService.findByNumero(dto.getNumero()).get().toString()));
                }
            default:
                LOGGER.info("default");
                break;
        }




    }


   /* @KafkaListener(topics = "#{@collecteTopic.name}",groupId = "group_id")
    public void processDocumentForCollect(@Payload String payload) throws JsonProcessingException {


        List<String> attributesList = new ArrayList<>();

        // Parse the JSON string into a JsonNode object
        JsonNode jsonNode = objectMapper.readTree(payload);

        // Extract keys from the JsonNode object
        Iterator<String> fieldNames = jsonNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            attributesList.add(fieldName);
        }

        for (String attribute : attributesList) {
            log.info(attribute);
        }

        collectMessage = payload;

    }*/

    public String getTypageMessage(){
        return this.typageMessage;
    }

    public String getCollectMessage(){
        return this.collectMessage;
    }





}


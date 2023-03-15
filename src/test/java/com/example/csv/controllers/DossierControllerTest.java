package com.example.csv.controllers;

import com.example.csv.services.DossierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class DossierControllerTest {


    private static final Logger logger = LoggerFactory.getLogger(TiersControllerTest.class);
    private DossierController controller;
    @Mock
    private DossierService service;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp(){
        controller= new DossierController(service);
    }
    @Test
    void getMetadata() throws Exception {
        List<String> attributes = new ArrayList<>(List.of("id","dossier_DC","numero","listSDC","n_DPS","montant_du_pres"));
        MvcResult result = mvc.perform(get("/api/csv/dossier/attributes"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedContent = new ObjectMapper().writeValueAsString(attributes);
        String actualContent = result.getResponse().getContentAsString();
        assertEquals(expectedContent, actualContent);
        logger.info("expectedContent: " + expectedContent + " actualContent: " + actualContent);
    }

    @Test
    void uploadFileSuccessfully() throws Exception {
        //create a Mock CSV file with some content
        String csvContent = "dossier_DC,numero,listSDC,n_DPS,montant_du_pres\n1,iheb,@gmail.com,cherif\n2,ahmed,.@gmail.com,tounsi\n";
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.multipart("/api/csv/tier/upload").file(mockFile))
                .andExpect(status().isOk())
                .andReturn();


    }





    @Test
    void save() {
    }

    @Test
    void getAllDossiers() {
    }

    @Test
    void searchDossier() {
    }

    @Test
    void getDossier() {
    }

    @Test
    void deleteDossier() {
    }

    @Test
    void updateDossier() {
    }
}

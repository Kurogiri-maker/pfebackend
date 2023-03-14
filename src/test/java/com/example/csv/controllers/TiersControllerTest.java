package com.example.csv.controllers;

import com.example.csv.domain.Tiers;
import com.example.csv.helper.CSVHelper;
import com.example.csv.services.TiersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class TiersControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(TiersControllerTest.class);


    private TiersController controller;

    @Mock
    private TiersService service;

    @Autowired
    private MockMvc mvc;


    @BeforeEach
    void setUp(){
        controller= new TiersController(service);
    }


    @Test
    void getMetadata() throws Exception {
        List<String> attributes = new ArrayList<>(List.of("id","numero","nom" ,"siren","ref_mandat"));

        MvcResult result = mvc.perform(get("/api/csv/tier/attributes"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedContent = new ObjectMapper().writeValueAsString(attributes);
        String actualContent = result.getResponse().getContentAsString();

        assertEquals(expectedContent,actualContent);

        logger.info(actualContent);
    }

    @Test
    void uploadFileSuccesfully() throws Exception {

        // create a mock CSV file with some content
        String csvContent = "Numero,nom,siren,ref_mandat\n1,iheb,@gmail.com,cherif\n2,ahmed,.@gmail.com,tounsi\n";
        MockMultipartFile mockCsvFile = new MockMultipartFile("file","test.csv", "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        //doNothing().when(service).saveFile(mockCsvFile);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.multipart("/api/csv/tier/upload").file(mockCsvFile))
                .andExpect(status().isOk())
                .andReturn();


        String expectedContent = "Uploaded the file successfully: test.csv";
        String actualContent = result.getResponse().getContentAsString();
        String val = JsonPath.read(actualContent, "$.message");

        assertEquals(expectedContent,val);

        logger.info("Expected : " + expectedContent);
        logger.info("Resultat  : " + val);


    }
    @Test
    void uploadFileFailed() throws Exception {

        // create a mock CSV file with some content
        String csvContent = "numero,Nom,siren,ref_mandat\n1,iheb,@gmail.com,cherif\n2,ahmed,.@gmail.com,tounsi\n";
        MockMultipartFile mockCsvFile = new MockMultipartFile("file","test.csv", "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        CSVHelper csvHelperMock = mock(CSVHelper.class);
        doAnswer(invocation -> {
            csvHelperMock.csvToTiers(invocation.getArgument(0, MultipartFile.class).getInputStream());
            return null;
        }).when(csvHelperMock).csvToTiers(any(InputStream.class));

        doNothing().when(service).saveFile(any(MultipartFile.class));

        service.saveFile(mockCsvFile);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.multipart("/api/csv/tier/upload").file(mockCsvFile))
                .andExpect(status().isOk())
                .andReturn();


        String expectedContent = "Could not upload the file: test.csv!";
        String actualContent = result.getResponse().getContentAsString();
        String val = JsonPath.read(actualContent, "$.message");

        assertEquals(expectedContent,val);

        logger.info("Expected : " + expectedContent);
        logger.info("Resultat  : " + val);

    }

    @Test
    void save() {
    }

    @Test
    void getAllTiers() {
    }

    @Test
    void getTiers() {
    }

    @Test
    void searchByName() {
    }

    @Test
    void searchTiers() {
    }

    @Test
    void deleteTiers() {
    }

    @Test
    void updateTiers() {
    }
}

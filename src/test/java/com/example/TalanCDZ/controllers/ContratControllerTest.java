package com.example.TalanCDZ.controllers;

import com.example.TalanCDZ.DTO.ContratDTO;
import com.example.TalanCDZ.domain.Contrat;
import com.example.TalanCDZ.domain.ResponseMessage;
import com.example.TalanCDZ.services.AdditionalAttributesContratService;
import com.example.TalanCDZ.services.ContratService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContratControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(ContratControllerTest.class);

    private ContratController controller;

    private AdditionalAttributesContratService additionalAttributesContratService;

    @Mock
    private ContratService service;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {

        controller = new ContratController(service, additionalAttributesContratService);
    }

    @Test
    void getMetadata() throws Exception {
        List<String> attributes = new ArrayList<String>(List.of("id", "numero","raisonSocial","codeProduit","produit","phase","montantPret"));

        MvcResult result = mvc.perform(get("/api/csv/contrat/attributes"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedContent = new ObjectMapper().writeValueAsString(attributes);
        String actualContent = result.getResponse().getContentAsString();

        assertEquals(expectedContent, actualContent);

        logger.info(actualContent);
    }

    @Test
    void uploadFileSuccessfully() throws Exception {
        // create a mock CSV file with some content
        String csvContent = "numero,raisonSocial,codeProduit,produit,phase,montantPret\ndfydn,1,ztfop,amgqv,fmkzu,zqmyl\ndfydn,1,ztfop,amgqv,fmkzu,zqmyl\n";
        MockMultipartFile mockCsvFile = new MockMultipartFile("file", "test.csv", "text/csv",
                csvContent.getBytes(StandardCharsets.UTF_8));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.multipart("/api/csv/contrat/upload").file(mockCsvFile))
                .andExpect(status().isOk())
                .andReturn();

        String expectedContent = "Uploaded the file successfully: test.csv";
        String actualContent = result.getResponse().getContentAsString();
        String val = JsonPath.read(actualContent, "$.message");

        assertEquals(expectedContent, val);

        logger.info("Expected : " + expectedContent);
        logger.info("Resultat  : " + val);
    }

    @Test
    void uploadFileFailed() throws Exception {

        // create a mock CSV file with some content
        String csvContent = "numero,raisonSocial,codeProduit,produit,phase,montantPret\ndfydn,1,ztfop,amgqv,fmkzu,zqmyl\ndfydn,1,ztfop,amgqv,fmkzu,zqmyl\n";
        MultipartFile mockCsvFile = new MockMultipartFile("file", "test.csv", "text/csv",
                csvContent.getBytes(StandardCharsets.UTF_8));

        doThrow(new RuntimeException("exception")).when(service).saveFile(mockCsvFile);

        ResponseEntity<ResponseMessage> result = controller.uploadFile(mockCsvFile);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.saveFile(mockCsvFile);
        });

        String expectedContent = "Could not upload the file: test.csv!";
        String actualContent = result.getBody().getMessage();
        assertEquals(expectedContent, actualContent);
        assertEquals(HttpStatus.EXPECTATION_FAILED, result.getStatusCode());

        logger.info("Expected : " + expectedContent + " Response status : " + HttpStatus.EXPECTATION_FAILED);
        logger.info("Resultat  : " + actualContent + " Response status : " + result.getStatusCode());

    }

    @Test
    void uploadFileWrongFormat() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test file".getBytes());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.multipart("/api/csv/contrat/upload").file(file))
                .andExpect(status().isBadRequest())
                .andReturn();

        String expectedContent = "Please upload a csv file!";
        String actualContent = result.getResponse().getContentAsString();
        String val = JsonPath.read(actualContent, "$.message");

        assertEquals(expectedContent, val);

        logger.info("Expected : " + expectedContent);
        logger.info("Resultat  : " + val);
    }

    @Test
    void save() throws Exception {
        Contrat c = new Contrat(
                1L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(c);
        MvcResult result = mvc.perform(post("/api/csv/contrat").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResult = result.getResponse().getContentAsString();
        logger.info(body);
        logger.info(actualResult);
        assertEquals(body, actualResult);
    }

    @Test
    void getAllContrats() {
        Contrat t1 = new Contrat(
                1L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        Contrat t2 = new Contrat(
                2L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);

        List<Contrat> content = new ArrayList<>(List.of(t1, t2));
        Page<Contrat> page = new PageImpl<>(content, PageRequest.of(0, 2), 2);

        when(service.getAllContrats(0, 2, null)).thenReturn(page);

        ResponseEntity<Page<Contrat>> result = controller.getAllContrats(0, 2, null);

        List<Contrat> actualResult = result.getBody().getContent();
        assertEquals(actualResult, page.getContent());
        logger.info(String.valueOf(actualResult));
        logger.info(String.valueOf(result.getStatusCode()));

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getContrat() {
        Contrat t1 = new Contrat(
                1L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        when(service.getContrat(1L)).thenReturn(t1);
        ResponseEntity<Contrat> result = controller.getContrat(1L);

        Contrat actualContent = result.getBody();
        logger.info(String.valueOf(actualContent));
        assertEquals(t1, actualContent);
        String actualStatus = result.getStatusCode().toString();
        logger.info(actualStatus);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getContratFailed() {
        Contrat t1 = new Contrat(
                1L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        when(service.getContrat(1L)).thenReturn(null);
        ResponseEntity<Contrat> result = controller.getContrat(1L);

        Contrat actualContent = result.getBody();
        logger.info(String.valueOf(actualContent));
        assertNotEquals(t1, actualContent);

        String actualStatus = result.getStatusCode().toString();
        logger.info(actualStatus);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void deleteContrat() {
        Contrat t1 = new Contrat(
                1L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        when(service.getContrat(t1.getId())).thenReturn(t1);
        ResponseEntity<Void> result = controller.deleteContrat(t1.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void deleteContratFailed() {
        Contrat t1 = new Contrat(
                1L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        when(service.getContrat(t1.getId())).thenReturn(null);
        ResponseEntity<Void> result = controller.deleteContrat(t1.getId());
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

    }

    @Test
    void updateContart() {
        Contrat t1 = new Contrat(
                1L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        ContratDTO dto = new ContratDTO();
        dto.setRaisonSocial("aaaa");
        ResponseEntity<Void> result = controller.updateTiers(1L, t1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
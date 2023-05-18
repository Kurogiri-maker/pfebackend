package com.example.TalanCDZ.controllers;

import com.example.TalanCDZ.DTO.DossierDTO;
import com.example.TalanCDZ.domain.Dossier;
import com.example.TalanCDZ.domain.ResponseMessage;
import com.example.TalanCDZ.services.AdditionalAttributesDossierService;
import com.example.TalanCDZ.services.DossierService;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class DossierControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(DossierControllerTest.class);

    private DossierController controller;
    private AdditionalAttributesDossierService additionalAttributesDossierService;

    @Mock
    private DossierService service;



    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {

        controller = new DossierController(service, additionalAttributesDossierService);
    }

    @Test
    void getMetadata() throws Exception {

        List<String> attributes = new ArrayList<>(
                List.of("id","dossierDC","numero","listSDC","n_DPS","montant_du_pres"));

        MvcResult result = mvc.perform(get("/api/csv/dossier/attributes"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedContent = new ObjectMapper().writeValueAsString(attributes);
        String actualContent = result.getResponse().getContentAsString();

        assertEquals(expectedContent, actualContent);

        logger.info(actualContent);
    }

    @Test
    void uploadFileSuccesfully() throws Exception {

        // create a mock CSV file with some content
        String csvContent = "dossier_DC,Numero,ListSDC,N_DPS,Montant_du_pres\ndossier1,1,list1,1,10\ndossier2,2,list2,2,20\n";
        MockMultipartFile mockCsvFile = new MockMultipartFile("file", "test.csv", "text/csv",
                csvContent.getBytes(StandardCharsets.UTF_8));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.multipart("/api/csv/dossier/upload").file(mockCsvFile))
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
        String csvContent = "dossier_DC,Numero,ListSDC,N_DPS,Montant_du_pres\ndossier1,1,list1,1,10\ndossier2,2,list2,2,20\n";
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
        MvcResult result = mvc.perform(MockMvcRequestBuilders.multipart("/api/csv/dossier/upload").file(file))
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
        Dossier t = new Dossier(1L, "dossier1", "1", "list1", "1", "10",null);
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(t);
        MvcResult result = mvc.perform(post("/api/csv/dossier").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResult = result.getResponse().getContentAsString();
        logger.info(body);
        logger.info(actualResult);
        assertEquals(body, actualResult);
    }

    @Test
    void getAllDossiers() {
        Dossier t1 = new Dossier(1L, "dossier1", "1", "list1", "1", "10",null);
        Dossier t2 = new Dossier(2L, "dossier2", "2", "list2", "2", "20",null);
        List<Dossier> content = new ArrayList<>();
        content.add(t1);
        content.add(t2);
        Page<Dossier> page = new PageImpl<>(content, PageRequest.of(0, 2), 2);

        when(service.getAllDossiers(0, 2, null)).thenReturn(page);

        ResponseEntity<Page<Dossier>> result = controller.getAllDossiers(0, 2, null);

        List<Dossier> actualResult = result.getBody().getContent();
        assertEquals(actualResult, page.getContent());
        logger.info(String.valueOf(actualResult));
        logger.info(String.valueOf(result.getStatusCode()));

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchDossier() {
        Dossier t1 = new Dossier(1L, "dossier1", "1", "list1", "1", "10",null);
        Dossier t2 = new Dossier(2L, "dossier1", "2", "list2", "2", "20",null);
        List<Dossier> list = new ArrayList<>(List.of(t1, t2));
        when(service.searchDossiers("dossier1", null, null, null)).thenReturn(list);
        ResponseEntity<List<Dossier>> result = controller.searchDossier("dossier1", null, null, null);

        List<Dossier> actualResult = result.getBody();
        assertEquals(actualResult, list);
        logger.info(String.valueOf(actualResult));
        logger.info(String.valueOf(result.getStatusCode()));
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getDossier() {
        Dossier t1 = new Dossier(1L, "dossier1", "1", "list1", "1", "10",null);
        when(service.getDossier(1L)).thenReturn(t1);
        ResponseEntity<Dossier> result = controller.getDossier(1L);

        Dossier actualContent = result.getBody();
        logger.info(String.valueOf(actualContent));
        assertEquals(t1, actualContent);
        String actualStatus = result.getStatusCode().toString();
        logger.info(actualStatus);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void deleteDossier() {
        Dossier t1 = new Dossier(1L, "dossier1", "1", "list1", "1", "10",null);
        when(service.getDossier(t1.getId())).thenReturn(t1);
        ResponseEntity<Void> result = controller.deleteDossier(t1.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void deleteDossierFailed() {
        Dossier t1 = new Dossier(1L, "dossier1", "1", "list1", "1", "10",null);
        when(service.getDossier(t1.getId())).thenReturn(null);
        ResponseEntity<Void> result = controller.deleteDossier(t1.getId());
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

    }

    @Test
    void updateDossier() {

        Dossier t1 = new Dossier(1L, "dossier1", "1", "list1", "1", "10",null);
        DossierDTO dto = new DossierDTO();
        dto.setDossierDC("dossier2");
        ResponseEntity<Void> result = controller.updateDossier(1L, t1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}

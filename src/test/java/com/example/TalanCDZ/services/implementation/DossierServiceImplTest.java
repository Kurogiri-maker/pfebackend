package com.example.TalanCDZ.services.implementation;

import com.example.TalanCDZ.DTO.DossierDTO;
import com.example.TalanCDZ.domain.Dossier;
import com.example.TalanCDZ.helper.TopicProducer;
import com.example.TalanCDZ.helper.mapper.DossierMapper;
import com.example.TalanCDZ.repositories.DossierRepository;
import com.example.TalanCDZ.services.AdditionalAttributesDossierService;
import com.example.TalanCDZ.services.DossierService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DossierServiceImplTest {


    private DossierService service;

    @Mock
    private  TopicProducer topicProducer;


    private AdditionalAttributesDossierService additionalService;

    @Mock
    private DossierMapper mapper;
    @Mock
    private DossierRepository dossierRepo;


    @BeforeEach
    void setup() {
        service = new DossierServiceImpl(dossierRepo,topicProducer,additionalService,mapper);

    }


    @Test
    void saveDossier() {
        Dossier t = new Dossier(1L, "dossier1", "1", "list1", "1","10",null);
        List<Dossier> list = new ArrayList<>();
        list.add(t);
        when(dossierRepo.save(t)).thenReturn(t);
        Dossier t1 = service.save(t);

        when(dossierRepo.findAll()).thenReturn(list);
        List<Dossier> result = service.getAllDossiers();

        assertNotNull(t1);
        log.info("Dossier : " + !t1.equals(null));
        assertEquals(1, result.size());
        log.info("Expected : 1 ; Result: " + result.size());

    }


    @Test
    void updateDossier() {
        Dossier t = new Dossier(1L, "dossier1", "1", "list1", "1","10",null);
        DossierDTO dto = new DossierDTO();
        dto.setDossierDC("dossier2");
        Long id = 1L;
        Dossier t1 = new Dossier(1L, "dossier2", "1", "list1", "1","10",null);
        when(dossierRepo.findById(id)).thenReturn(Optional.of(t));
        when(mapper.mapNonNullFields(dto, t)).thenReturn(t1);
        service.update(id, dto);
        ArgumentCaptor<Dossier> arg = ArgumentCaptor.forClass(Dossier.class);
        verify(dossierRepo).save(arg.capture());
        Dossier result = arg.getValue();


        assertEquals("dossier2", result.getDossierDC());
        log.info("Expected : dossier2" + "Result : " + result.getDossierDC());

    }


    @Test
    void deleteDossier() {
        Dossier t = new Dossier(1L, "dossier1", "1", "list1", "1","10",null);
        doNothing().when(dossierRepo).deleteById(t.getId());
        service.delete(t.getId());
        try {
            verify(dossierRepo, times(1)).deleteById(t.getId());
            log.info("The verify() call was successful.");
        } catch (MockitoException e) {
            log.error("The verify() call failed: " + e.getMessage());
        }

    }


    @Test
    void SaveCsvFile() throws Exception {
        // create a mock CSV file with some content
        String csvContent = "dossier_DC,Numero,ListSDC,N_DPS,Montant_du_pres\ndossier1,1,list1,1,10\ndossier2,2,list2,2,20\n";
        MockMultipartFile mockCsvFile = new MockMultipartFile("test.csv", null, "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        service.saveFile(mockCsvFile);

        ArgumentCaptor<List<Dossier>> arg =  ArgumentCaptor.forClass(List.class);
        verify(dossierRepo).saveAll(arg.capture());
        List<Dossier> result = arg.getValue();
        assertEquals(2,result.size());
        String content="dossier_DC,Numero,ListSDC,N_DPS,Montant_du_pres\n";
        for (Dossier d : result) {
            content += d.getDossierDC()+","+d.getNumero() + "," + d.getListSDC() + "," + d.getN_DPS() + "," + d.getMontant_du_pres() + "\n";
        }
        assertEquals(csvContent, content);
    }

    @Test
    void testSaveFileWithIOException() throws Exception {
        // create a mock MultipartFile that throws an IOException when its input stream is accessed
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getInputStream()).thenThrow(new IOException("File could not be read"));

        // verify that a RuntimeException is thrown when the file is saved
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.saveFile(mockFile);
        });

        // verify that the exception message is correct
        String expectedMessage = "File could not be read";
        String actualMessage = exception.getCause().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void getDossier(){
        Dossier t = new Dossier(1L, "dossier1", "1", "list1", "1","10",null);
        when(dossierRepo.findById(1L)).thenReturn(Optional.of(t));
        Dossier t1 = service.getDossier(1L);
        assertEquals(t.getId(),t1.getId());
    }

    @Test
    void getAllDossiers(){
        Dossier t = new Dossier(1L, "dossier1", "1", "list1", "1","10",null);
        Dossier t1 = new Dossier(2L, "dossier2", "2", "list2", "2","20",null);


        List<Dossier> list = new ArrayList<>();
        list.add(t);
        list.add(t1);

        when(dossierRepo.findAll()).thenReturn(list);
        List<Dossier> result = service.getAllDossiers();
        Dossier t2 = result.get(0);
        Dossier t3 = result.get(1);
        assertNotNull(t2);
        assertNotNull(t3);
        assertEquals(t2.getDossierDC(),t.getDossierDC());
        assertEquals(t3.getMontant_du_pres(),t1.getMontant_du_pres());
        assertEquals(list.size(),result.size());
    }




    @Test
    void searchDossier(){
        Dossier t = new Dossier(1L, "dossier1", "1", "list1", "1","10",null);
        String dossier_DC = "iheb";
        List<Dossier> list = new ArrayList<>();
        list.add(t);
        service.save(t);
        when(dossierRepo.findAll(any(Specification.class))).thenReturn(list);
        List<Dossier>  result = service.searchDossiers(dossier_DC,null,null,null);
        log.info("Expected : "+ list.size() + "\n Result : "+ result.size());
        assertEquals(list.size(),result.size());
        log.info("Expected : "+ list.get(0).getDossierDC() + "\n Result : "+ result.get(0).getDossierDC());
        assertEquals(list.get(0).getDossierDC(),result.get(0).getDossierDC());

    }

    @Test
    void searchDossierForCoverage(){
        Dossier t = new Dossier(1L, "dossier1", "1", "list1", "1","10",null);
        Dossier t1 = new Dossier(2L, "dossier2", "2", "list2", "2","20",null);
        Dossier t2 = new Dossier(3L, "dossier3", "3", "list3", "3","30",null);
        String dossier_DC = "dossier1";
        String listSDC = "list1";
        String n_DPS = "1";
        String montant_du_pres = "10";
        List<Dossier> list = new ArrayList<>(List.of(t,t1,t2));
        for (Dossier dossier : list) {
            service.save(dossier);
        }
        when(dossierRepo.findAll(any(Specification.class))).thenReturn(List.of(t));
        List<Dossier>  result = service.searchDossiers(dossier_DC,listSDC,n_DPS,montant_du_pres);
        log.info("Expected : 1 " + "\n Result : "+ result.size());
        assertEquals(1,result.size());
        log.info("Expected : "+ list.get(0) + "\n Result : "+ result.get(0));
        assertEquals(list.get(0),result.get(0));

    }


    @Test
    void getAllDossiersPage(){
        Dossier t = new Dossier(1L, "dossier1", "1", "list1", "1","10",null);
        Dossier t1 = new Dossier(2L, "dossier2", "2", "list2", "2","20",null);


        List<Dossier> list = new ArrayList<>();
        list.add(t);
        list.add(t1);

        Page<Dossier> page = new PageImpl<>(list);

        when(dossierRepo.findAll(PageRequest.of(0, 2, Sort.by("id")))).thenReturn(page);
        Page<Dossier> result = service.getAllDossiers(0,2,"id");

        assertEquals(page.getTotalElements(), result.getTotalElements());
        log.info("Expected : "+ page.getTotalElements() + "\n Result : "+ result.getTotalElements());
        assertEquals(page.getContent(), result.getContent());
        log.info("Expected : "+ page.getContent() + "\n Result : "+ result.getContent());


    }

    @Test
    void getAllDossiersEmptyPage(){


        List<Dossier> list = new ArrayList<>();

        Page<Dossier> page = new PageImpl<>(list);

        when(dossierRepo.findAll(PageRequest.of(0, 2, Sort.by("id")))).thenReturn(page);
        Page<Dossier> result = service.getAllDossiers(0,2,"id");

        assertEquals(page.getTotalElements(), result.getTotalElements());
        log.info("Expected : "+ page.getTotalElements() + "\n Result : "+ result.getTotalElements());
        assertEquals(page.getContent(), result.getContent());
        log.info("Expected : "+ page.getContent() + "\n Result : "+ result.getContent());


    }
}



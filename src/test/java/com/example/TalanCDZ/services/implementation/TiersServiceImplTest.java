package com.example.TalanCDZ.services.implementation;


import com.example.TalanCDZ.DTO.TiersDTO;
import com.example.TalanCDZ.domain.Tiers;
import com.example.TalanCDZ.helper.mapper.TierMapper;
import com.example.TalanCDZ.repositories.TiersRepository;
import com.example.TalanCDZ.services.TiersService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
public class TiersServiceImplTest {


    private TiersService service;

    @Mock
    private TierMapper mapper;
    @Mock
    private TiersRepository tiersRepo;

    @BeforeEach
    void setup() {
        service = new TiersServiceImpl(tiersRepo, mapper);
    }


    @Test
    void saveTiers() {
        Tiers t = new Tiers(1L, "1", "Iheb", "iheb.cherif99@gmail.com", "cherif");
        List<Tiers> list = new ArrayList<>();
        list.add(t);
        when(tiersRepo.save(t)).thenReturn(t);
        Tiers t1 = service.save(t);

        when(tiersRepo.findAll()).thenReturn(list);
        List<Tiers> result = service.getAllTiers();

        assertNotNull(t1);
        log.info("Tier : " + !t1.equals(null));
        assertEquals(1, result.size());
        log.info("Expected : 1 ; Result: " + result.size());

    }


    @Test
    void updateTiers() {
        Tiers t = new Tiers(1L, "1", "Iheb", "iheb.cherif99@gmail.com", "cherif");
        TiersDTO dto = new TiersDTO();
        dto.setNom("Ahmed");
        Long id = 1L;
        Tiers t1 = new Tiers(1L, "1", "Ahmed", "iheb.cherif99@gmail.com", "cherif");
        when(tiersRepo.findById(id)).thenReturn(Optional.of(t));
        when(mapper.mapNonNullFields(dto, t)).thenReturn(t1);
        service.update(id, dto);
        ArgumentCaptor<Tiers> arg = ArgumentCaptor.forClass(Tiers.class);
        verify(tiersRepo).save(arg.capture());
        Tiers result = arg.getValue();


        assertEquals("Ahmed", result.getNom());
        log.info("Expected : Ahmed" + "Result : " + result.getNom());

    }


    @Test
    void deleteTiers() {
        Tiers t = new Tiers(1L, "1", "Iheb", "iheb.cherif99@gmail.com", "cherif");
        doNothing().when(tiersRepo).deleteById(t.getId());
        service.delete(t.getId());
        try {
            verify(tiersRepo, times(1)).deleteById(t.getId());
            log.info("The verify() call was successful.");
        } catch (MockitoException e) {
            log.error("The verify() call failed: " + e.getMessage());
        }

    }


    @Test
    void SaveCsvFile() throws Exception {
        // create a mock CSV file with some content
        String csvContent = "Numero,nom,siren,ref_mandat\n1,iheb,@gmail.com,cherif\n2,ahmed,.@gmail.com,tounsi\n";
        MockMultipartFile mockCsvFile = new MockMultipartFile("test.csv", null, "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        service.saveFile(mockCsvFile);

        ArgumentCaptor<List<Tiers>> arg =  ArgumentCaptor.forClass(List.class);
        verify(tiersRepo).saveAll(arg.capture());
        List<Tiers> result = arg.getValue();
        assertEquals(2,result.size());
        String content="Numero,nom,siren,ref_mandat\n";
        for (Tiers tiers : result) {
            content += tiers.getNumero() + "," + tiers.getNom() + "," + tiers.getSiren() + "," + tiers.getRef_mandat() + "\n";
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
    void getTiers(){
        Tiers t = new Tiers(1L, "1", "Iheb", "iheb.cherif99@gmail.com", "cherif");
        when(tiersRepo.findById(1L)).thenReturn(Optional.of(t));
        Tiers t1 = service.getTiers(1L);
        assertEquals(t.getId(),t1.getId());
    }

    @Test
    void getAllTiers(){
        Tiers t = new Tiers(null,"1","iheb",".@gmail.com","cherif");
        Tiers t1 = new Tiers(null,"2","ahmed",".@gmail.com","tounsi");


        List<Tiers> list = new ArrayList<>();
        list.add(t);
        list.add(t1);

        when(tiersRepo.findAll()).thenReturn(list);
        List<Tiers> result = service.getAllTiers();
        Tiers t2 = result.get(0);
        Tiers t3 = result.get(1);
        assertNotNull(t2);
        assertNotNull(t3);
        assertEquals(t2.getNom(),t.getNom());
        assertEquals(t3.getRef_mandat(),t1.getRef_mandat());
        assertEquals(list.size(),result.size());
    }


    @Test
    void search(){
        Tiers t = new Tiers(1L, "1", "Iheb", "iheb.cherif99@gmail.com", "cherif");
        String name = "Iheb";
        List<Tiers> list = new ArrayList<>();
        list.add(t);
        when(tiersRepo.findAllByNom(name)).thenReturn(list);
        List<Tiers> result = service.search(name);
        assertEquals(list.size(),result.size());
        assertEquals(list.get(0).getNom(),result.get(0).getNom());

    }

    @Test
    void searchTiers(){
        Tiers t = new Tiers(1L, "1", "Iheb", "iheb.cherif99@gmail.com", "cherif");
        Tiers t1 = new Tiers(2L,"2","ahmed",".@gmail.com","tounsi" );
        Tiers t2 = new Tiers(3L,"3","ahmed","@yahoo.fr","chaari" );
        String name = "Iheb";
        List<Tiers> list = new ArrayList<>(List.of(t,t1,t2));
        for (Tiers tiers : list) {
            service.save(tiers);
        }
        when(tiersRepo.findAll(any(Specification.class))).thenReturn(List.of(t));
        List<Tiers>  result = service.searchTiers(name,"iheb.cherif99@gmail.com","cherif");
        log.info("Expected : "+ 1 + "\n Result : "+ result.size());
        assertEquals(1,result.size());
        log.info("Expected : "+ list.get(0).getNom() + "\n Result : "+ result.get(0).getNom());
        assertEquals(list.get(0).getNom(),result.get(0).getNom());

    }


    @Test
    void getAllTiersPage(){
        Tiers t = new Tiers(2L,"1","iheb",".@gmail.com","cherif");
        Tiers t1 = new Tiers(1L,"2","ahmed",".@gmail.com","tounsi");
        List<Tiers> list = new ArrayList<>();
        list.add(t);
        list.add(t1);

        Page<Tiers> page = new PageImpl<>(list);

        when(tiersRepo.findAll(PageRequest.of(0, 2, Sort.by("id")))).thenReturn(page);
        Page<Tiers> result = service.getAllTiers(0,2,"id");

        assertEquals(page.getTotalElements(), result.getTotalElements());
        log.info("Expected : "+ page.getTotalElements() + "\n Result : "+ result.getTotalElements());
        assertEquals(page.getContent(), result.getContent());
        log.info("Expected : "+ page.getContent() + "\n Result : "+ result.getContent());
    }

    @Test
    void getAllTiersEmptyPage(){


        List<Tiers> list = new ArrayList<>();

        Page<Tiers> page = new PageImpl<>(list);

        when(tiersRepo.findAll(PageRequest.of(0, 2, Sort.by("id")))).thenReturn(page);
        Page<Tiers> result = service.getAllTiers(0,2,"id");

        assertEquals(page.getTotalElements(), result.getTotalElements());
        log.info("Expected : "+ page.getTotalElements() + "\n Result : "+ result.getTotalElements());
        assertEquals(page.getContent(), result.getContent());
        log.info("Expected : "+ page.getContent() + "\n Result : "+ result.getContent());


    }
}







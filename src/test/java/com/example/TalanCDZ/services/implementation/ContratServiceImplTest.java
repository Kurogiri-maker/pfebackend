package com.example.TalanCDZ.services.implementation;


import com.example.TalanCDZ.DTO.ContratDTO;
import com.example.TalanCDZ.domain.Contrat;
import com.example.TalanCDZ.helper.CSVHelper;
import com.example.TalanCDZ.helper.mapper.ContratMapper;
import com.example.TalanCDZ.repositories.ContratRepository;
import com.example.TalanCDZ.services.AdditionalAttributesContratService;
import com.example.TalanCDZ.services.AdditionalAttributesDossierService;
import com.example.TalanCDZ.services.ContratService;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
class ContratServiceImplTest {

    private AdditionalAttributesContratService additionalService;

    @Mock
    private ContratRepository contratRepository;
    private ContratService contratService ;
    @Mock
    private ContratMapper mapper;

    @BeforeEach
    void setUp() {
        contratService = new ContratServiceImpl(contratRepository,additionalService,mapper);
    }

    @Test
    void save() {

        //given

        Contrat contrat = new Contrat(
                null,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        contratService.save(contrat);

        ArgumentCaptor<Contrat> contratArgumentCaptor = ArgumentCaptor.forClass(Contrat.class);
        verify(contratRepository).save(contratArgumentCaptor.capture());

        Contrat capturedContrat = contratArgumentCaptor.getValue();
        //then
        assertEquals(capturedContrat,contrat);


    }

    @Test
    void testGetContrat() {
        Contrat expectedContrat = new Contrat(
                31L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);



        when(contratRepository.findById(31L)).thenReturn(Optional.of(expectedContrat));
        Contrat actualContrat = contratService.getContrat(31L);
        assertEquals(expectedContrat, actualContrat);
        verify(contratRepository).findById(31L);



    }



    @Test
    void updateContrat(){
        Contrat contrat = new Contrat(
                21L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);

        ContratDTO contratDTO = new ContratDTO();
        contratDTO.setRaisonSocial("3");
        Contrat contrat1 = new Contrat(
                21L,
                "dfydn",
                "3",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);

        when(contratRepository.findById(21L)).thenReturn(Optional.of(contrat));
        when(mapper.mapNonNullFields(contratDTO,contrat)).thenReturn(contrat1);

        //then
        contratService.update(contrat.getId(), contratDTO);
        ArgumentCaptor<Contrat> contratArgumentCaptor = ArgumentCaptor.forClass(Contrat.class);
        verify(contratRepository).save(contratArgumentCaptor.capture());
        Contrat capturedContrat = contratArgumentCaptor.getValue();

        //assert
        assertEquals(capturedContrat,contrat1);
        log.info("Expected : 3" + "Result : " + capturedContrat.getRaisonSocial());

    }

    @Test
    void delete() {

        Contrat contrat = new Contrat(
                31L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        contratService.delete(contrat.getId());

        Mockito.verify(contratRepository).deleteById(contrat.getId());

    }



    @Test
    public void saveFile_shouldSaveContrats()  {
        // create a mock for the ContratRepository

        // create some test data
        byte[] fileContent = "numero,raisonSocial,codeProduit,produit,phase,montantPret\ndfydn,1,ztfop,amgqv,fmkzu,zqmyl\ndfydn,1,ztfop,amgqv,fmkzu,zqmyl\n".getBytes();
        MockMultipartFile file = new MockMultipartFile("file.csv", "file.csv", "text/csv", fileContent);

        // create a list of expected Contrat objects
        List<Contrat> expectedContrats = new ArrayList<>();
        expectedContrats.add( new Contrat(
                null,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null));
        expectedContrats.add( new Contrat(
                null,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null));

        // create a mock CSVHelper that returns the expected Contrat objects
        CSVHelper csvHelper = Mockito.mock(CSVHelper.class);


        // create an instance of the class under test


        // call the method under test
        contratService.saveFile(file);

        // verify that the ContratRepository.saveAll method was called with the expected Contrat objects
        Mockito.verify(contratRepository).saveAll(expectedContrats);
    }

    @Test
    void testSaveFileWithIOException() throws Exception {
        // create a mock MultipartFile that throws an IOException when its input stream is accessed
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getInputStream()).thenThrow(new IOException("File could not be read"));

        // verify that a RuntimeException is thrown when the file is saved
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contratService.saveFile(mockFile);
        });

        // verify that the exception message is correct
        String expectedMessage = "File could not be read";
        String actualMessage = exception.getCause().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void testGetAllContrat() {
        // create some test data
        Contrat contrat1 = new Contrat(
                1L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        Contrat contrat2= new Contrat(
                2L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        List<Contrat> expectedContrats = new ArrayList<>();
        expectedContrats.add(contrat1);
        expectedContrats.add(contrat2);
        // create a mock repository and configure it to return the test data
        ContratRepository mockRepo = mock(ContratRepository.class);
        when(mockRepo.findAll()).thenReturn(expectedContrats);

        // create a service instance with the mock repository
        ContratServiceImpl contratService = new ContratServiceImpl(mockRepo,additionalService,mapper);


        // call the method and verify the results
        List<Contrat> actualContrats = contratService.getAllContrats();
        assertEquals(expectedContrats, actualContrats);
        verify(mockRepo).findAll();
    }

    @Test
    void getAllContrats() {

        Contrat contrat1 = new Contrat(
                1L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        Contrat contrat2= new Contrat(
                2L,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                null);
        List<Contrat> expectedContrats = new ArrayList<>(List.of(contrat1, contrat2));


        Page<Contrat> page = new PageImpl<>(expectedContrats);
        when(contratRepository.findAll(any(Pageable.class))).thenReturn(page);

        when(contratRepository.findAll(PageRequest.of(0, 2, Sort.by("id")))).thenReturn(page);
        Page<Contrat> result = contratService.getAllContrats(0, 2, "id");

        assertEquals(page.getContent(), result.getContent());
        assertEquals(page.getTotalElements(), result.getTotalElements());

    }

    @Test
    void getAllContratsEmptyPage() {
        List<Contrat> expectedContrats = new ArrayList<>(List.of());


        Page<Contrat> page = new PageImpl<>(expectedContrats);
        when(contratRepository.findAll(any(Pageable.class))).thenReturn(page);

        when(contratRepository.findAll(PageRequest.of(0, 2, Sort.by("id")))).thenReturn(page);
        Page<Contrat> result = contratService.getAllContrats(0, 2, "id");

        assertEquals(page.getContent(), result.getContent());
        assertEquals(page.getTotalElements(), result.getTotalElements());

    }
}
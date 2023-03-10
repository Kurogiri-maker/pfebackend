package com.example.csv.services.implementation;

import com.example.csv.domain.Contrat;
import com.example.csv.services.ContratService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@Slf4j
class ContratServiceImplTest {

    @Autowired
    private ContratService contratService;

    //test the save method
    @Test
    void save() {
        int size = contratService.getAllContrats().size();
        //given

        Contrat contrat = new Contrat(
                null,
                "num_dossier",
                "num_cp",
                "raison_Social",
                "id_Tiers",
                "num_dc",
                "num_sdc",
                "num_cir",
                "num_siren",
                "ref_coll",
                "code_produit",
                "id_de_offre_comm",
                "chef_de_file",
                "num_ovi",
                "num_rum",
                "typeenregie",
                "produit_comm",
                "produit",
                "phase",
                "montant_pret");

        //then
        contratService.save(contrat);


        int expected = size+1;
        assertEquals(expected,contratService.getAllContrats().size());
        log.info("Expected : " + expected);
        log.info("Result : "+ contratService.getAllContrats().size() );

    }

    @Test
    void saveFile() {
        // Arrange

        int size = contratService.getAllContrats().size();


        String csvContent = "Num_dossierKPS,Num_CP,Raison_Social,Id_Tiers,Num_DC,Num_SDC,Num_CIR,Num_SIREN,Ref_Collaborative,Code_Produit,Identifiant_de_offre_comm,Chef_de_File,Num_OVI,Num_RUM,TypeEnergie,Produit_Comm,Produit,Phase,Montant_pret\n" +
                "dfydn,1,ztfop,amgqv,fmkzu,zqmyl,bfixm,yyvwp,vegzu,ixrrl,wmeoi,dcosp,wpinz,nliuy,impvq,uljpk,blcbp,poocm,yobnt\n";
        MultipartFile csvFile = new MockMultipartFile("test.csv", csvContent.getBytes(StandardCharsets.UTF_8));
        List<Contrat> contrats = new ArrayList<>();
        contrats.add(  new Contrat(
                null,
                "dfydn",
                "1",
                "ztfop",
                "amgqv",
                "fmkzu",
                "zqmyl",
                "bfixm",
                "yyvwp",
                "vegzu",
                "ixrrl",
                "wmeoi",
                "dcosp",
                "wpinz",
                "nliuy",
                "impvq",
                "uljpk",
                "blcbp",
                "poocm",
                "yobnt"));




        // Act

        contratService.saveFile(csvFile);

        int expected= size+1;
        assertEquals(expected,contratService.getAllContrats().size());
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
    void getAllContrats() {
    }

    @Test
    void getContrat() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void testGetAllContrats() {
    }
}
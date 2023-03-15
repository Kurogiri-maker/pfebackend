package com.example.csv.controllers;

import com.example.csv.repositories.ContratRepository;
import com.example.csv.services.ContratService;
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
import org.springframework.test.web.servlet.MockMvc;


@Slf4j
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ContratControllerTest {



    private static final Logger logger = LoggerFactory.getLogger(TiersControllerTest.class);
    private ContratController controller;
    @Mock
    private ContratService service;
    @Autowired
    private MockMvc mvc;



    @BeforeEach
    void setUp(){
        controller= new ContratController(service);
    }

    @Test
    void uploadFile() {
    }

    @Test
    void getMetadata() {

    }

    @Test
    void save() {
    }

    @Test
    void getAllContrats() {
    }

    @Test
    void getContrat() {
    }

    @Test
    void deleteContrat() {
    }

    @Test
    void updateTiers() {
    }
}

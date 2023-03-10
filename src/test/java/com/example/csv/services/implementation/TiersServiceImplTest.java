package com.example.csv.services.implementation;


import com.example.csv.DTO.TiersDTO;
import com.example.csv.domain.Tiers;
import com.example.csv.helper.mapper.TierMapper;
import com.example.csv.repositories.TiersRepository;
import com.example.csv.services.TiersService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void setup(){
         service = new TiersServiceImpl(tiersRepo,mapper);
    }




    @Test
     void saveTiers(){
        Tiers t = new Tiers(1L,"1","Iheb","iheb.cherif99@gmail.com","cherif");
        List<Tiers> list = new ArrayList<>();
        list.add(t);
        when(tiersRepo.save(t)).thenReturn(t);
        Tiers t1 = service.save(t);

        when(tiersRepo.findAll()).thenReturn(list);
        List<Tiers> result = service.getAllTiers();

        assertNotNull(t1);
        log.info("Tier : " + !t1.equals(null));
        assertEquals(1,result.size());
        log.info("Expected : 1 ; Result: " + result.size());

    }


    @Test
    void UpdateTiers(){
        Tiers t = new Tiers(1L,"1","Iheb","iheb.cherif99@gmail.com","cherif");
        TiersDTO dto = new TiersDTO();
        dto.setNom("Ahmed");
        Long id = 1L;
        Tiers t1 = new Tiers(1L,"1","Ahmed","iheb.cherif99@gmail.com","cherif");
        when(tiersRepo.findById(id)).thenReturn(Optional.of(t));
        when(mapper.mapNonNullFields(dto,t)).thenReturn(t1);
        service.update(id,dto);
        ArgumentCaptor<Tiers> arg = ArgumentCaptor.forClass(Tiers.class);
        verify(tiersRepo).save(arg.capture());
        Tiers result = arg.getValue();


        assertEquals("Ahmed",result.getNom());
        log.info("Expected : Ahmed"+"Result : "+result.getNom());

    }

}

package com.example.csv.services;

import com.example.csv.domain.Tiers;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TiersService {

    Tiers save(Tiers tiers);

    void saveFile(MultipartFile file);

    List<Tiers> getAllTiers();

    Tiers getTiers(Long id);

    void delete(Long id);


    void update();
}

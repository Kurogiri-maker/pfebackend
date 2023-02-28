package com.example.csv.services;

import com.example.csv.domain.Tiers;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TiersService {
    void save(MultipartFile file);

    List<Tiers> getAllTiers();

    Tiers getTiers(Long id);

    void delete(Long id);


}

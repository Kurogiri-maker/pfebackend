package com.example.csv.services;

import com.example.csv.DTO.TiersDTO;
import com.example.csv.domain.Tiers;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TiersService {

    Tiers save(Tiers tiers);

    void saveFile(MultipartFile file);

    List<Tiers> getAllTiers();

    Page<Tiers> getAllTiers(Integer pageNo, Integer pageSize, String sortBy);

    Tiers getTiers(Long id);

    void delete(Long id);

    void update(Long id, TiersDTO dto);

    List<Tiers>  search(String nom);

    List<Tiers> searchTiers(String nom , String siren , String ref_mandat);

    //List<Tiers> searchTiers(String searchTerm);
}

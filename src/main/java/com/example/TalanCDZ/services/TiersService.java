package com.example.TalanCDZ.services;

import com.example.TalanCDZ.DTO.TiersDTO;
import com.example.TalanCDZ.domain.Tiers;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TiersService {

    Tiers save(Tiers tiers);

    void saveFile(MultipartFile file);

    List<Tiers> getAllTiers();

    List<Tiers> searchTiers(String searchTerm);

    Page<Tiers> getAllTiers(Integer pageNo, Integer pageSize, String sortBy);

    Tiers getTiers(Long id);

    void delete(Long id);

    void update(Long id, TiersDTO dto);

    List<Tiers>  search(String nom);

    List<Tiers> searchTiers(String nom , String siren , String ref_mandat);

    Optional<Tiers> findByNumero(String numero);

    //List<Tiers> searchTiers(String searchTerm);
}

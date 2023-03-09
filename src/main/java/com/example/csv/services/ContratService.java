package com.example.csv.services;

import com.example.csv.DTO.ContratDTO;
import com.example.csv.domain.Contrat;
import com.example.csv.domain.Tiers;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContratService {
    Contrat save(Contrat contrat);

    void saveFile(MultipartFile file);

    public List<Contrat> getAllContrats();

    Contrat getContrat(Long id);

    void delete(Long id);

    void update(Long id, ContratDTO contratDTO);

    public Page<Contrat> getAllContrats(Integer pageNo, Integer pageSize, String sortBy);
}

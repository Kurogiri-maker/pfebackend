package com.example.TalanCDZ.services;

import com.example.TalanCDZ.DTO.ContratDTO;
import com.example.TalanCDZ.domain.AdditionalAttributesContrat;
import com.example.TalanCDZ.domain.Contrat;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ContratService {
    Contrat save(Contrat contrat);

    void saveFile(MultipartFile file);

    public List<Contrat> getAllContrats();

    Contrat getContrat(Long id);

    void delete(Long id);

    void update(Long id, ContratDTO contratDTO);

    public Page<Contrat> getAllContrats(Integer pageNo, Integer pageSize, String sortBy);

    List<Contrat> searchContrat(String searchTerm);

    Optional<Contrat> findByNumero(String numero);

}

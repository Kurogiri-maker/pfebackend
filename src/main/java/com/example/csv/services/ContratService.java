package com.example.csv.services;

import com.example.csv.domain.Contrat;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContratService {
    void save(MultipartFile file);
    public List<Contrat> getAllContrat();
}

package com.example.csv.services.implementation;

import com.example.csv.domain.Contrat;
import com.example.csv.domain.Tiers;
import com.example.csv.helper.CSVHelper;
import com.example.csv.repositories.ContratRepository;
import com.example.csv.services.ContratService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ContratServiceImpl implements ContratService {

    @Autowired
    private final ContratRepository contratRepo;

    @Override
    public void save(MultipartFile file) {

        try {
            List<Contrat> contrats = CSVHelper.csvToContrats(file.getInputStream());
            contratRepo.saveAll(contrats);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

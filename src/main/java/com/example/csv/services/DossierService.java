package com.example.csv.services;

import org.springframework.web.multipart.MultipartFile;

public interface DossierService {
    void save(MultipartFile file);
}

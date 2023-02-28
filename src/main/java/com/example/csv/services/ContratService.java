package com.example.csv.services;

import org.springframework.web.multipart.MultipartFile;

public interface ContratService {
    void save(MultipartFile file);
}

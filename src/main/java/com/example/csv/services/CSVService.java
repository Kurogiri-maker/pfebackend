package com.example.csv.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;


@Service
@AllArgsConstructor
public class CSVService {


    public List<String> getColumnsHeader(InputStream inputStream) {
        return null;
    }

    public void save(MultipartFile file) {
    }
}

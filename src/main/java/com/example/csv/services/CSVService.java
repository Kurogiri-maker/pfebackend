package com.example.csv.services;

import com.example.csv.domain.Tutorial;
import com.example.csv.helper.CSVHelper;
import com.example.csv.repositories.TutorialRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@AllArgsConstructor
public class CSVService {

    @Autowired
    private final TutorialRepository tutoRepo;

    public void save(MultipartFile file) {
        try {
            List<Tutorial> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
            tutoRepo.saveAll(tutorials);
            getColumnsHeader(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<Tutorial> getAllTutorials() {
        return tutoRepo.findAll();
    }

    public List<String> getColumnsHeader(InputStream is) throws UnsupportedEncodingException { return CSVHelper.getCSVHeader(is);}
}

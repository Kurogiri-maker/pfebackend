package com.example.csv.services;

import com.example.csv.repositories.TutorialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TutorialServiceImpl implements   TutorialService{

    private final TutorialRepository tutoRepo;
}

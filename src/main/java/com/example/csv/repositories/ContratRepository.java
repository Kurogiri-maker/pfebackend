package com.example.csv.repositories;

import com.example.csv.domain.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratRepository extends JpaRepository<Contrat,Long> {

}

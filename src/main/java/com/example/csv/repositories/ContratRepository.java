package com.example.csv.repositories;

import com.example.csv.domain.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratRepository extends JpaRepository<Contrat,Long> {
}

package com.example.csv.repositories;

import com.example.csv.domain.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierRepository extends JpaRepository<Dossier, Long> {
}

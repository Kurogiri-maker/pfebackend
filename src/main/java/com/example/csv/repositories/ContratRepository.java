package com.example.csv.repositories;

import com.example.csv.domain.Contrat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratRepository extends JpaRepository<Contrat,Long> {

    Page<Contrat> findAll(Pageable pageable);

}

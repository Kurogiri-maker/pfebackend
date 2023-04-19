package com.example.TalanCDZ.repositories;

import com.example.TalanCDZ.domain.Contrat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratRepository extends JpaRepository<Contrat, Long> {

    Page<Contrat> findAll(Pageable pageable);

}

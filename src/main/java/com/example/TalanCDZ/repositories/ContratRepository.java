package com.example.TalanCDZ.repositories;

import com.example.TalanCDZ.domain.Contrat;
import com.example.TalanCDZ.domain.Tiers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContratRepository extends JpaRepository<Contrat, Long> , JpaSpecificationExecutor<Contrat> {

    Page<Contrat> findAll(Pageable pageable);

}

package com.example.TalanCDZ.repositories;

import com.example.TalanCDZ.domain.Dossier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DossierRepository extends JpaRepository<Dossier, Long>, JpaSpecificationExecutor<Dossier> {

    Page<Dossier> findAll(Pageable pageable);

    Optional<Dossier> findByNumero(String numero);
}

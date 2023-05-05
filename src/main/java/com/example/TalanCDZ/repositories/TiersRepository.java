package com.example.TalanCDZ.repositories;

import com.example.TalanCDZ.domain.Tiers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TiersRepository extends JpaRepository<Tiers, Long>, JpaSpecificationExecutor<Tiers> {

    Page<Tiers> findAll(Pageable pageable);

    List<Tiers> findAllByNom(String nom);

    Optional<Tiers> findByNumero(String numero);

}

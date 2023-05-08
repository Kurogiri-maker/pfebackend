package com.example.TalanCDZ.repositories;

import com.example.TalanCDZ.domain.AdditionalAttributesContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdditionalAttributesContratRepository extends JpaRepository<AdditionalAttributesContrat,Long> {
    @Query("SELECT DISTINCT cle FROM AdditionalAttributesContrat")
    List<String> findDistinctCle();
}

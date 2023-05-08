package com.example.TalanCDZ.repositories;

import com.example.TalanCDZ.domain.AdditionalAttributesDossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdditionalAttributesDossierRepository extends JpaRepository<AdditionalAttributesDossier,Long> {

    @Query("SELECT DISTINCT cle FROM AdditionalAttributesDossier")
    List<String> findDistinctCle();
}

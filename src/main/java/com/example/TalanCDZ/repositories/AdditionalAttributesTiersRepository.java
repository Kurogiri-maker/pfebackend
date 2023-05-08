package com.example.TalanCDZ.repositories;


import com.example.TalanCDZ.domain.AdditionalAttributesTiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdditionalAttributesTiersRepository extends JpaRepository <AdditionalAttributesTiers,Long> {

    @Query("SELECT DISTINCT cle FROM AdditionalAttributesTiers")
    List<String> findDistinctCle();

}

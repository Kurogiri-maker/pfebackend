package com.example.csv.repositories;

import com.example.csv.domain.Tiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface TiersRepository extends JpaRepository<Tiers,Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Tiers t SET t.nom = :nom, t.siren = :siren,t.ref_mandat = :ref_mandat  WHERE t.id = :id")
    void updateTiers(@Param("id") Long id, @Param("nom") String nom, @Param("siren") String siren, @Param("ref_mandat") String ref_mandat);
}

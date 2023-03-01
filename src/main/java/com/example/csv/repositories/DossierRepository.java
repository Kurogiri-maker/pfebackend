package com.example.csv.repositories;

import com.example.csv.domain.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface DossierRepository extends JpaRepository<Dossier, Long>{

        @Transactional
        @Modifying
        @Query("UPDATE Dossier d SET d.dossier_DC = :dossier_DC, d.listSDC = :listSDC,d.n_DPS = :n_DPS,d.montant_du_pres= :montant_du_pres  WHERE d.id = :id")
        void updateDossier(@Param("id") Long id, @Param("dossier_DC") String dossier_DC, @Param("listSDC") String listSDC, @Param("n_DPS") String n_DPS, @Param("montant_du_pres") String montant_du_pres);
}

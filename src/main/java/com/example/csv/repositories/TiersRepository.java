package com.example.csv.repositories;

import com.example.csv.domain.Tiers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TiersRepository extends JpaRepository<Tiers,Long> , JpaSpecificationExecutor<Tiers>{

    Page<Tiers> findAll(Pageable pageable);

    List<Tiers> findAllByNom(String nom);


}

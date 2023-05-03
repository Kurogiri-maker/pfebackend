package com.example.TalanCDZ.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalAttributesTiers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cle;
    private String valeur;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "tiers_id")
    private Tiers tiers;

}

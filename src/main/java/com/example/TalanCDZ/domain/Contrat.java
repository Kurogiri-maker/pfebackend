package com.example.TalanCDZ.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String num_dossierKPS;
    private String num_CP;
    private String raison_Social;
    private String id_Tiers;
    private String num_DC;
    private String num_SDC;
    private String num_CIR;
    private String num_SIREN;
    private String ref_Collaborative;
    private String code_Produit;
    private String identifiant_de_offre_comm;
    private String chef_de_File;
    private String num_OVI;
    private String num_RUM;
    private String typeEnergie;
    private String produit_Comm;
    private String produit;
    private String phase;
    private String montant_pret;


}

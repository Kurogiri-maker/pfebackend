package com.example.csv.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class Contrat {

    @Id
    @GeneratedValue
    private Long id;
    private String Num_dossierKPS;
    private String Num_CP;
    private String Raison_Social;
    private String Id_Tiers;
    private String Num_DC;
    private String Num_SDC;
    private String Num_CIR;
    private String Num_SIREN;
    private String Ref_Collaborative;
    private String Code_Produit;
    private String Identifiant_de_offre_comm;
    private String Chef_de_File;
    private String Num_OVI;
    private String Num_RUM;
    private String TypeEnergie;
    private String Produit_Comm;
    private String Produit;
    private String Phase;
    private String Montant_pret;

    public Contrat(String num_dossierKPS, String num_CP, String raison_Social, String id_Tiers, String num_DC, String num_SDC, String num_CIR, String num_SIREN, String ref_Collaborative, String code_Produit, String identifiant_de_offre_comm, String chef_de_File, String num_OVI, String num_RUM, String typeEnergie, String produit_Comm, String produit, String phase, String montant_pret) {
        Num_dossierKPS = num_dossierKPS;
        Num_CP = num_CP;
        Raison_Social = raison_Social;
        Id_Tiers = id_Tiers;
        Num_DC = num_DC;
        Num_SDC = num_SDC;
        Num_CIR = num_CIR;
        Num_SIREN = num_SIREN;
        Ref_Collaborative = ref_Collaborative;
        Code_Produit = code_Produit;
        Identifiant_de_offre_comm = identifiant_de_offre_comm;
        Chef_de_File = chef_de_File;
        Num_OVI = num_OVI;
        Num_RUM = num_RUM;
        TypeEnergie = typeEnergie;
        Produit_Comm = produit_Comm;
        Produit = produit;
        Phase = phase;
        Montant_pret = montant_pret;
    }


    public Contrat() {

    }
}

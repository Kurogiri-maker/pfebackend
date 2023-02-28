package com.example.csv.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor
public class Dossier {

    @Id
    @GeneratedValue
    private Long id;
    private String dossier_DC;
    private String numero;
    private String listSDC;
    private String n_DPS;
    private String montant_du_pres;


    public Dossier(String dossier_dc, String numero, String listSDC, String n_dps, String montant_du_pres) {
    }
}

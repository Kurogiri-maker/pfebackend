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

    public Dossier(String dossier_DC, String numero, String listSDC, String n_DPS, String montant_du_pres) {
        this.dossier_DC = dossier_DC;
        this.numero = numero;
        this.listSDC = listSDC;
        this.n_DPS = n_DPS;
        this.montant_du_pres = montant_du_pres;
    }
}

package com.example.csv.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class Tiers {

    @Id
    @GeneratedValue
    private Long id;
    private String numero;
    private String nom;
    private String siren;
    private String ref_mandat;


    public Tiers( ) {
    }

    public Tiers(String numero, String nom, String siren, String ref_mandat) {
        this.numero = numero;
        this.nom = nom;
        this.siren = siren;
        this.ref_mandat = ref_mandat;
    }
}

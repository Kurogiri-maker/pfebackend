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
    private String numero;
    private String raisonSocial;
    private String codeProduit;
    private String produit;
    private String phase;
    private String montantPret;


    public boolean equals(Contrat c){
        return this.raisonSocial.equals(c.raisonSocial) && this.codeProduit.equals(c.codeProduit) && this.produit.equals(c.produit) && this.phase.equals(c.phase) && this.montantPret.equals(c.montantPret);
    }


}

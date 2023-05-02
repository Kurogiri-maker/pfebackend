package com.example.TalanCDZ.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    @OneToMany
    @JoinColumn(name = "contrat_id")
    private List<AdditionalAttributesContrat> additional;



    public boolean equals(Contrat c){
        return this.raisonSocial.equals(c.raisonSocial) && this.codeProduit.equals(c.codeProduit) && this.produit.equals(c.produit) && this.phase.equals(c.phase) && this.montantPret.equals(c.montantPret);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrat contrat = (Contrat) o;
        return Objects.equals(numero, contrat.numero) && Objects.equals(raisonSocial, contrat.raisonSocial) && Objects.equals(codeProduit, contrat.codeProduit) && Objects.equals(produit, contrat.produit) && Objects.equals(phase, contrat.phase) && Objects.equals(montantPret, contrat.montantPret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, raisonSocial, codeProduit, produit, phase, montantPret);
    }
}

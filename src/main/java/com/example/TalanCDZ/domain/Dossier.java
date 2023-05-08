package com.example.TalanCDZ.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dossier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dossierDC;
    private String numero;
    private String listSDC;
    private String n_DPS;
    private String montant_du_pres;
    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AdditionalAttributesDossier> additionalAttributesSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dossier dossier = (Dossier) o;
        return Objects.equals(dossierDC, dossier.dossierDC) && Objects.equals(numero, dossier.numero) && Objects.equals(listSDC, dossier.listSDC) && Objects.equals(n_DPS, dossier.n_DPS) && Objects.equals(montant_du_pres, dossier.montant_du_pres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dossierDC, numero, listSDC, n_DPS, montant_du_pres);
    }


    /*public boolean equals(Dossier d){
        return this.dossier_DC.equals(d.dossier_DC) && this.listSDC.equals(d.listSDC) && this.n_DPS.equals(d.n_DPS) && this.montant_du_pres.equals(d.montant_du_pres);
    }*/

}

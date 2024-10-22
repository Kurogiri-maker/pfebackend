package com.example.TalanCDZ.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tiers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String nom;
    private String siren;
    private String refMandat;
    @OneToMany(mappedBy = "tiers", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<AdditionalAttributesTiers> additionalAttributesSet = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tiers tiers = (Tiers) o;
        return Objects.equals(numero, tiers.numero) && Objects.equals(nom, tiers.nom) && Objects.equals(siren, tiers.siren) && Objects.equals(refMandat, tiers.refMandat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, nom, siren, refMandat);
    }

    @Override
    public String toString() {
        return "Tiers{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", nom='" + nom + '\'' +
                ", siren='" + siren + '\'' +
                ", ref_mandat='" + refMandat + '\'' +
                ", additionalAttributesTiersList=" + additionalAttributesSet +
                '}';
    }

    /*public void addAdditionalAttributesTiers(AdditionalAttributesTiers additionalAttributesTiers){
        if (this.additionalAttributesTiersList==null){
            this.additionalAttributesTiersList=HashSet(additionalAttributesTiers);
        }
        else{
            this.additionalAttributesTiersList.add(additionalAttributesTiers);
        }
    }*/
}

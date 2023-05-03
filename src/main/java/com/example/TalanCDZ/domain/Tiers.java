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
public class Tiers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String nom;
    private String siren;
    private String ref_mandat;
    @OneToMany(mappedBy = "tiers")
    private List<AdditionalAttributesTiers> additionalAttributesTiersList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tiers tiers = (Tiers) o;
        return Objects.equals(numero, tiers.numero) && Objects.equals(nom, tiers.nom) && Objects.equals(siren, tiers.siren) && Objects.equals(ref_mandat, tiers.ref_mandat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, nom, siren, ref_mandat);
    }
}

package com.example.TalanCDZ.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    /*
    @OneToMany
    @JoinColumn(name = "tiers_id")
    private List<Attribute> additional;
    */



    public boolean equals(Tiers t){
        return this.nom.equals(t.nom) && this.siren.equals(t.siren) && this.ref_mandat.equals(t.ref_mandat);
    }



}

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
public class Dossier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dossier_DC;
    private String numero;
    private String listSDC;
    private String n_DPS;
    private String montant_du_pres;
    @OneToMany
    @JoinColumn(name = "dossier_id")
    private List<AdditionalAttributesDossier> additional;


    public boolean equals(Dossier d){
        return this.dossier_DC.equals(d.dossier_DC) && this.listSDC.equals(d.listSDC) && this.n_DPS.equals(d.n_DPS) && this.montant_du_pres.equals(d.montant_du_pres);
    }

}

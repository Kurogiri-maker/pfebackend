package com.example.TalanCDZ.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalAttributesContrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cle;
    private String valeur;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "contrat_id")
    private Contrat contrat;

    @Override
    public String toString() {
        return "AdditionalAttributesContrat{" +
                "id=" + id +
                ", cle='" + cle + '\'' +
                ", valeur='" + valeur + '\'' +
                '}';
    }
}

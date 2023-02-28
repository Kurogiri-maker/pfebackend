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
    private String nom;
    private String siren;
    private String ref_mandat;

    public Tiers(){

    }
}

package com.example.TalanCDZ.helper;

import com.example.TalanCDZ.domain.Tiers;
import org.springframework.data.jpa.domain.Specification;

public class TiersSpecifications {
    public static Specification<Tiers> nomContains(String nom) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("nom")),  nom);
    }

    public static Specification<Tiers> sirenContains(String siren) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("siren")), siren );
    }

    public static Specification<Tiers> refMandatContains(String ref_mandat) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("ref_mandat")), ref_mandat );
    }
}

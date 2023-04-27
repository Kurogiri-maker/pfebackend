package com.example.TalanCDZ.helper;

import com.example.TalanCDZ.domain.Contrat;
import com.example.TalanCDZ.domain.Tiers;
import org.springframework.data.jpa.domain.Specification;

public class ContratSpecifications {

    public static Specification<Contrat> numeroContains(String numero) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("numero")),  numero);
    }

    public static Specification<Contrat> raisonSocialContains(String raisonSocial) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("raisonSocial")),  raisonSocial);
    }

    public static Specification<Contrat> codeProduitContains(String codeProduit) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("codeProduit")),  codeProduit);
    }

    public static Specification<Contrat> produitContains(String produit) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("produit")),  produit);
    }

    public static Specification<Contrat> phaseContains(String phase) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("phase")),  phase);
    }

    public static Specification<Contrat> montantPretContains(String montantPret) {
        return (root,query,builder) -> builder.like(builder.lower(root.get("montantPret")),  montantPret);
    }
}

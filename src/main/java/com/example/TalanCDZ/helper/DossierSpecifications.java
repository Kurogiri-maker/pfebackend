package com.example.TalanCDZ.helper;

import com.example.TalanCDZ.domain.Dossier;
import org.springframework.data.jpa.domain.Specification;

public class DossierSpecifications {
    public static Specification<Dossier> dossierDCContains(String dossier_dc) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("dossier_DC")), dossier_dc);
    }

    public static Specification<Dossier> listSDCContains(String listSDC) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("listSDC")), listSDC);
    }

    public static Specification<Dossier> nDPSContains(String n_dps) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("n_DPS")), n_dps);
    }

    public static Specification<Dossier> montantDuPresContains(String montant_du_pres) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("montant_du_pres")), montant_du_pres);
    }
}

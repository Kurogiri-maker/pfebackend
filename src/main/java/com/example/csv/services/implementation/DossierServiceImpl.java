package com.example.csv.services.implementation;

import com.example.csv.DTO.DossierDTO;
import com.example.csv.domain.Dossier;
import com.example.csv.domain.Tiers;
import com.example.csv.helper.CSVHelper;
import com.example.csv.helper.DossierSpecifications;
import com.example.csv.helper.TiersSpecifications;
import com.example.csv.helper.mapper.DossierMapper;
import com.example.csv.repositories.DossierRepository;
import com.example.csv.services.DossierService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class DossierServiceImpl implements DossierService {
    @Autowired
    private final DossierRepository dosRepo;

    private DossierMapper mapper;

    @Override
    public void saveFile(MultipartFile file) {
        try {
            List<Dossier> dossiers = CSVHelper.csvToDossiers(file.getInputStream());
            dosRepo.saveAll(dossiers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Dossier> getAllDossiers() {
        return dosRepo.findAll();
    }

    @Override
    public Dossier getDossier(Long id) {
        return dosRepo.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        dosRepo.deleteById(id);
    }

    @Override
    public Dossier save(Dossier dossier) {
        Dossier dossier1 = dosRepo.save(dossier);
        return dossier1;
    }

    @Override
    public void update(Long id, DossierDTO dto) {
        Dossier d = dosRepo.findById(id).get();
        if(d != null){
            Dossier d1 = mapper.mapNonNullFields(dto , d);
            dosRepo.save(d1);
        }

    }

    @Override
    public List<Dossier> searchDossiers(String dossier_DC, String listSDC, String n_DPS, String montant_du_pres) {
        Specification<Dossier> spec =Specification.where(null);

        if( dossier_DC != null && !dossier_DC.isEmpty()){
            spec = spec.and(DossierSpecifications.dossierDCContains(dossier_DC));
        }

        if( listSDC != null && !listSDC.isEmpty()){
            spec = spec.and(DossierSpecifications.listSDCContains(listSDC));
        }

        if( n_DPS != null && !n_DPS.isEmpty()){
            spec = spec.and(DossierSpecifications.nDPSContains(n_DPS));
        }

        if( montant_du_pres != null && !montant_du_pres.isEmpty()){
            spec = spec.and(DossierSpecifications.montantDuPresContains(montant_du_pres));
        }


        return dosRepo.findAll(spec);
    }

}

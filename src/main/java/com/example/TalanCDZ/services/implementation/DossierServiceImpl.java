package com.example.TalanCDZ.services.implementation;

import com.example.TalanCDZ.DTO.DossierDTO;
import com.example.TalanCDZ.domain.Dossier;
import com.example.TalanCDZ.helper.CSVHelper;
import com.example.TalanCDZ.helper.DossierSpecifications;
import com.example.TalanCDZ.helper.mapper.DossierMapper;
import com.example.TalanCDZ.repositories.DossierRepository;
import com.example.TalanCDZ.services.DossierService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Specification<Dossier> spec = Specification.where(null);

        if (dossier_DC != null && !dossier_DC.isEmpty()) {
            spec = spec.and(DossierSpecifications.dossierDCContains(dossier_DC));
        }

        if (listSDC != null && !listSDC.isEmpty()) {
            spec = spec.and(DossierSpecifications.listSDCContains(listSDC));
        }

        if (n_DPS != null && !n_DPS.isEmpty()) {
            spec = spec.and(DossierSpecifications.nDPSContains(n_DPS));
        }

        if (montant_du_pres != null && !montant_du_pres.isEmpty()) {
            spec = spec.and(DossierSpecifications.montantDuPresContains(montant_du_pres));
        }


        return dosRepo.findAll(spec);
    }

    @Override
    public List<Dossier> searchDossiers(String searchTerm) {
        Specification<Dossier> spec = Specification.where(DossierSpecifications.dossierDCContains(searchTerm)
                .or(DossierSpecifications.listSDCContains(searchTerm))
                .or(DossierSpecifications.nDPSContains(searchTerm))
                .or(DossierSpecifications.montantDuPresContains(searchTerm)));

        return dosRepo.findAll(spec);
    }

    public Page<Dossier> getAllDossiers(Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Dossier> pagedResult = dosRepo.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult;
        } else {
            return Page.empty();
        }
    }

}

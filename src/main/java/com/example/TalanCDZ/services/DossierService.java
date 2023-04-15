package com.example.TalanCDZ.services;

import com.example.TalanCDZ.DTO.DossierDTO;
import com.example.TalanCDZ.domain.Dossier;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DossierService {
    void saveFile(MultipartFile file);

    List<Dossier> getAllDossiers();

    Dossier getDossier(Long id);

    void delete(Long id);

    Dossier save(Dossier dossier);
    void update(Long id, DossierDTO dto);


    List<Dossier> searchDossiers(String dossier_DC, String listSDC, String n_DPS, String montant_du_pres);

    List<Dossier> searchDossiers(String searchTerm);

    public Page<Dossier> getAllDossiers(Integer pageNo, Integer pageSize, String sortBy);


}

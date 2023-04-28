package com.example.TalanCDZ.services.implementation;

import com.example.TalanCDZ.domain.AdditionalAttributesDossier;
import com.example.TalanCDZ.domain.AdditionalAttributesTiers;
import com.example.TalanCDZ.repositories.AdditionalAttributesDossierRepository;
import com.example.TalanCDZ.services.AdditionalAttributesDossierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdditionalAttributesDossierServiceImpl implements AdditionalAttributesDossierService {

    private final AdditionalAttributesDossierRepository repo;

    @Override
    public AdditionalAttributesDossier save(AdditionalAttributesDossier d) {
        return repo.save(d);
    }

    @Override
    public AdditionalAttributesDossier getAttribute(Long id) {
        Optional<AdditionalAttributesDossier> attribute = repo.findById(id);
        return attribute.orElse(null);    }

    @Override
    public List<AdditionalAttributesDossier> getAllAttribute() {
        List<AdditionalAttributesDossier> list = repo.findAll();
        if(list.isEmpty()){
            return null;
        }else {
            return list;
        }
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void update(Long id) {

    }
}

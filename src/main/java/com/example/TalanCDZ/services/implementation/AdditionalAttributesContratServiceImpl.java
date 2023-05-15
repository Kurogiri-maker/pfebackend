package com.example.TalanCDZ.services.implementation;

import com.example.TalanCDZ.DTO.AdditionalAttributesDTO;
import com.example.TalanCDZ.domain.AdditionalAttributesContrat;
import com.example.TalanCDZ.domain.AdditionalAttributesDossier;
import com.example.TalanCDZ.domain.AdditionalAttributesTiers;
import com.example.TalanCDZ.repositories.AdditionalAttributesContratRepository;
import com.example.TalanCDZ.services.AdditionalAttributesContratService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdditionalAttributesContratServiceImpl implements AdditionalAttributesContratService {

    private final AdditionalAttributesContratRepository repo;

    @Override
    public AdditionalAttributesContrat save(AdditionalAttributesContrat c) {
        return repo.save(c);    }

    @Override
    public AdditionalAttributesContrat getAttribute(Long id) {
        Optional<AdditionalAttributesContrat> attribute = repo.findById(id);
        return attribute.orElse(null);
    }

    @Override
    public List<AdditionalAttributesContrat> getAllAttribute() {
        List<AdditionalAttributesContrat> list = repo.findAll();
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
    public void update(Long id, AdditionalAttributesContrat dto) {
        Optional<AdditionalAttributesContrat> attribute = repo.findById(id);
        if(attribute.isPresent()){
            AdditionalAttributesContrat a = attribute.get();
            a.setCle(dto.getCle());
            a.setValeur(dto.getValeur());
            repo.save(a);
        }

    }

    @Override
    public List<String> getDistinctAttributeCle() {
        return repo.findDistinctCle();
    }
}

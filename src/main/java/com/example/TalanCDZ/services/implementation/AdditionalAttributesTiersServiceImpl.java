package com.example.TalanCDZ.services.implementation;

import com.example.TalanCDZ.domain.AdditionalAttributesTiers;
import com.example.TalanCDZ.repositories.AdditionalAttributesTiersRepository;
import com.example.TalanCDZ.services.AdditionalAttributesTiersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdditionalAttributesTiersServiceImpl implements AdditionalAttributesTiersService {

    private final AdditionalAttributesTiersRepository repo;

    @Override
    public AdditionalAttributesTiers save(AdditionalAttributesTiers t) {
        return repo.save(t);
    }

    @Override
    public AdditionalAttributesTiers getAttribute(Long id) {
        Optional<AdditionalAttributesTiers> attribute = repo.findById(id);
        return attribute.orElse(null);
    }

    @Override
    public List<AdditionalAttributesTiers> getAllAttribute() {
        List<AdditionalAttributesTiers> list = repo.findAll();
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


package com.example.TalanCDZ.services;

import com.example.TalanCDZ.domain.AdditionalAttributesContrat;

import java.util.List;

public interface AdditionalAttributesContratService {

    AdditionalAttributesContrat save(AdditionalAttributesContrat c);

    AdditionalAttributesContrat getAttribute(Long id);

    List<AdditionalAttributesContrat> getAllAttribute();

    void delete(Long id);

    void update(Long id);
}

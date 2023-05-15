package com.example.TalanCDZ.services;

import com.example.TalanCDZ.DTO.AdditionalAttributesDTO;
import com.example.TalanCDZ.domain.AdditionalAttributesDossier;
import com.example.TalanCDZ.domain.AdditionalAttributesTiers;

import java.util.List;

public interface AdditionalAttributesDossierService {

    AdditionalAttributesDossier save(AdditionalAttributesDossier d);

    AdditionalAttributesDossier getAttribute(Long id);

    List<AdditionalAttributesDossier> getAllAttribute();

    void delete(Long id);

    void update(Long id,  AdditionalAttributesDossier dto);


    List<String> getDistinctAttributeCle();
}

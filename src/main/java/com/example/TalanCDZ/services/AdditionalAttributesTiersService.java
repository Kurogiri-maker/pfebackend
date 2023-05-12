package com.example.TalanCDZ.services;

import com.example.TalanCDZ.DTO.AdditionalAttributesDTO;
import com.example.TalanCDZ.DTO.TiersDTO;
import com.example.TalanCDZ.domain.AdditionalAttributesTiers;

import java.util.List;

public interface AdditionalAttributesTiersService {

    AdditionalAttributesTiers save(AdditionalAttributesTiers t);

    AdditionalAttributesTiers getAttribute(Long id);

    List<AdditionalAttributesTiers> getAllAttribute();

    void delete(Long id);

    void update(Long id, AdditionalAttributesTiers dto);

    List<String> getDistinctAttributeCle();


}

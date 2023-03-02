package com.example.csv.helper.mapper;

import com.example.csv.DTO.DossierDTO;
import com.example.csv.DTO.TiersDTO;
import com.example.csv.domain.Dossier;
import com.example.csv.domain.Tiers;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DossierMapper {

    DossierDTO toDto(Dossier d);


    Dossier mapNonNullFields(DossierDTO dto, @MappingTarget Dossier d);

}

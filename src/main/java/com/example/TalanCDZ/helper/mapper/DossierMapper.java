package com.example.TalanCDZ.helper.mapper;

import com.example.TalanCDZ.DTO.DossierDTO;
import com.example.TalanCDZ.domain.Dossier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DossierMapper {

    DossierDTO toDto(Dossier d);

    Dossier mapNonNullFields(DossierDTO dto, @MappingTarget Dossier d);

}

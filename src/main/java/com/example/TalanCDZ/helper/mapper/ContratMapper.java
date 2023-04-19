package com.example.TalanCDZ.helper.mapper;

import com.example.TalanCDZ.DTO.ContratDTO;
import com.example.TalanCDZ.domain.Contrat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContratMapper {

    ContratDTO toDto(Contrat c);


    Contrat mapNonNullFields(ContratDTO dto, @MappingTarget Contrat c);


}

package com.example.csv.helper.mapper;

import com.example.csv.DTO.ContratDTO;
import com.example.csv.domain.Contrat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContratMapper {

    ContratDTO toDto(Contrat c);


    Contrat mapNonNullFields(ContratDTO dto, @MappingTarget Contrat c);


}

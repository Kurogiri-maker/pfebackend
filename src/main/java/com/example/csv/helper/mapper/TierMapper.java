package com.example.csv.helper.mapper;

import com.example.csv.DTO.TiersDTO;
import com.example.csv.domain.Tiers;
import org.mapstruct.*;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TierMapper {

    TiersDTO toDto(Tiers t);

    Tiers mapNonNullFields(TiersDTO dto, @MappingTarget Tiers t);
}

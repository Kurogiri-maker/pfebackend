package com.example.TalanCDZ.helper.mapper;

import com.example.TalanCDZ.DTO.TiersDTO;
import com.example.TalanCDZ.domain.Tiers;
import org.mapstruct.*;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TierMapper {

    TiersDTO toDto(Tiers t);

    Tiers mapNonNullFields(TiersDTO dto, @MappingTarget Tiers t);
}

package com.example.TalanCDZ.helper.mapper;

import com.example.TalanCDZ.DTO.UserDTO;
import com.example.TalanCDZ.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
        UserDTO toDto(User u);

        User mapNonNullFields(UserDTO dto, @MappingTarget User u);

}

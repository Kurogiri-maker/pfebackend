package com.example.csv.helper.mapper;

import com.example.csv.DTO.UserDTO;
import com.example.csv.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
        UserDTO toDto(User u);

        User mapNonNullFields(UserDTO dto, @MappingTarget User u);

}

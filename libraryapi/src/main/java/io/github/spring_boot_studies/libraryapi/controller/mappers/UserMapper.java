package io.github.spring_boot_studies.libraryapi.controller.mappers;

import io.github.spring_boot_studies.libraryapi.controller.dto.UserDTO;
import io.github.spring_boot_studies.libraryapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User toEntity(UserDTO userDTO);

}

package io.github.spring_boot_studies.libraryapi.controller.mappers;

import io.github.spring_boot_studies.libraryapi.controller.dto.AuthorDTO;
import io.github.spring_boot_studies.libraryapi.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
// O @ComponentModel é usado para indicar que o MapStruct deve gerar um componente Spring para o mapper.
public interface AuthorMapper {

  //  @Mapping(source = "name", target = "name") // Mapeia o campo name do AuthorDTO para o campo name do Author.
//  @Mapping(source = "dateBirth", target = "dateBirth") // Mapeia o campo dateBirth do AuthorDTO para o campo dateBirth do Author.
//  @Mapping(source = "nationality", target = "nationality") // Mapeia o campo nationality do AuthorDTO par o campo nationality do Author.
  Author toEntity(AuthorDTO authorDTO); // Método para converter AuthorDTO em Author.

  AuthorDTO toDTO(Author author); // Método para converter Author em AuthorDTO.

}

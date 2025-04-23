package io.github.spring_boot_studies.libraryapi.controller.mappers;

import io.github.spring_boot_studies.libraryapi.controller.dto.RegisterBookDTO;
import io.github.spring_boot_studies.libraryapi.controller.dto.ResponseResearchBookDTO;
import io.github.spring_boot_studies.libraryapi.model.Book;
import io.github.spring_boot_studies.libraryapi.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
// O @Mapper é usado para indicar que o MapStruct deve gerar um mapper para a classe. Uses é usado para indicar que o AuthorMapper deve ser usado para mapear o campo author do Book.
// O @ComponentModel é usado para indicar que o MapStruct deve gerar um componente Spring para o mapper.
public abstract class BookMapper { // O MapStruct permite a criação de um mapper usando uma classe abstrata.

  @Autowired
  AuthorRepository authorRepository;

  // expression é usado para indicar que o MapStruct deve usar uma expressão Java para mapear o campo author do RegisterBookDTO para o campo author do Book.
  @Mapping(target = "author", expression = "java( authorRepository.findById(registerBookDTO.idAuthor()).orElse(null) )")
  public abstract Book toEntity(RegisterBookDTO registerBookDTO);

  public abstract ResponseResearchBookDTO toDTO(Book book);
}

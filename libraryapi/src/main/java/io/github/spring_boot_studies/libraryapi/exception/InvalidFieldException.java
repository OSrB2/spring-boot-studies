package io.github.spring_boot_studies.libraryapi.exception;

import lombok.Getter;

public class InvalidFieldException extends RuntimeException {

  @Getter // Esse Getter dentro de uma exception é para poder pegar o campo que está inválido
  private String field;

  public InvalidFieldException(String field, String message) {
    super(message);
    this.field = field;
  }
}

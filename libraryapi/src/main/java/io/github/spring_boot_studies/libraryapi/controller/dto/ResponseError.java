package io.github.spring_boot_studies.libraryapi.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;
// Esse record é utilizado para o código de erro de resposta da API, A mensagem de erro e os campos que falharam na validação.
public record ResponseError(int status, String message, List<FieldErrorImpl> errors) {

  // Esse método é utilizado para criar um objeto de erro padrão, com o código de erro 400 e a mensagem de erro passada como parâmetro.
  public static ResponseError responseDefault(String message) {
    return new ResponseError(HttpStatus.BAD_REQUEST.value(), message, List.of());
  }

  // Esse método é utilizado para criar um objeto de erro padrão, com o código de erro 404 e a mensagem de erro passada como parâmetro.
  public static  ResponseError conflict(String message) {
    return new ResponseError(HttpStatus.CONFLICT.value(), message, List.of());
  }

}

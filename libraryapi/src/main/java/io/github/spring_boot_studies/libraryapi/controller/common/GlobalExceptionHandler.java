package io.github.spring_boot_studies.libraryapi.controller.common;

import io.github.spring_boot_studies.libraryapi.controller.dto.FieldErrorImpl;
import io.github.spring_boot_studies.libraryapi.controller.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class) // Esse método é chamado quando uma exceção do tipo MethodArgumentNotValidException é lançada
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // Esse método retorna o código de erro 422
  public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getFieldErrors();

    List<FieldErrorImpl> fieldErrorList = fieldErrors
        .stream()
        .map(fe -> new FieldErrorImpl(fe.getField(), fe.getDefaultMessage()))
        .collect(Collectors.toList());

    return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", fieldErrorList);
  }
}

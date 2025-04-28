package io.github.spring_boot_studies.libraryapi.controller.common;

import io.github.spring_boot_studies.libraryapi.controller.dto.FieldErrorImpl;
import io.github.spring_boot_studies.libraryapi.controller.dto.ResponseError;
import io.github.spring_boot_studies.libraryapi.exception.DuplicateRecordException;
import io.github.spring_boot_studies.libraryapi.exception.InvalidFieldException;
import io.github.spring_boot_studies.libraryapi.exception.OperationNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  // Esse método é chamado quando uma exceção do tipo MethodArgumentNotValidException é lançada
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // Esse método retorna o código de erro 422
  public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getFieldErrors();

    List<FieldErrorImpl> fieldErrorList = fieldErrors
        .stream()
        .map(fe -> new FieldErrorImpl(fe.getField(), fe.getDefaultMessage()))
        .collect(Collectors.toList());

    return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", fieldErrorList);
  }

  @ExceptionHandler(DuplicateRecordException.class)
  // Esse método é chamado quando uma exceção do tipo DuplicateRecordException é lançada
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseError handleDuplicateRecordException(DuplicateRecordException e) {
    return ResponseError.conflict(e.getMessage());
  }

  @ExceptionHandler(OperationNotPermittedException.class)
  // Esse método é chamado quando uma exceção do tipo OperationNotPermittedException é lançada
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseError handleOperationNotPermittedException(OperationNotPermittedException e) {
    return ResponseError.responseDefault(e.getMessage());
  }

  @ExceptionHandler(InvalidFieldException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseError handleInvalidFieldException(InvalidFieldException e) {
    return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
        "Validation error",
        List.of(new FieldErrorImpl(e.getField(), e.getMessage())));
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseError handleAccesDeniedException(AccessDeniedException e) {
    return new ResponseError(HttpStatus.FORBIDDEN.value(),
        "Access denied.", List.of());
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseError handleUnhandledErrors(RuntimeException e) {
    return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "An unexpected error has occurred. Please contact management.", List.of());
  }
}

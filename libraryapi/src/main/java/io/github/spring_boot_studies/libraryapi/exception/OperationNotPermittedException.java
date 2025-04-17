package io.github.spring_boot_studies.libraryapi.exception;

public class OperationNotPermittedException extends RuntimeException {
  public OperationNotPermittedException(String message) {
    super(message);
  }
}

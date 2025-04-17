package io.github.spring_boot_studies.libraryapi.exception;

public class DuplicateRecordException extends RuntimeException {
  public DuplicateRecordException(String message) {
    super(message);
  }
}

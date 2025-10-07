package io.github.spring.osrb2.rental_company.model.exception;

public class ReservationInvalidException extends RuntimeException {
  public ReservationInvalidException(String message) {
    super(message);
  }
}

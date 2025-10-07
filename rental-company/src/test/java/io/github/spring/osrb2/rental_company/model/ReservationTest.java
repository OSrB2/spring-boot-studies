package io.github.spring.osrb2.rental_company.model;

import io.github.spring.osrb2.rental_company.model.exception.ReservationInvalidException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationTest {
  Car car;
  Client client;

  @BeforeEach
  void config() {
    car = new Car("Sedan", 100.0);
    client = new Client("Maria");
  }

  @Test
  @DisplayName("Should create a reservation")
  void shouldCreateReservertion() throws Exception {
    Reservation reservation = new Reservation(client, car, 3);
    reservation.calculateTotalReservation(3);
    assertThat(reservation).isNotNull();
  }

  @Test
  @DisplayName("Should return a exception if days <= 0")
  void shouldReturnExceptionIfDays0orLess() throws Exception {

    // JUnit
    assertThrows(ReservationInvalidException.class, () -> new Reservation(client, car, 0));

    // AssertJ
    var error = Assertions.catchThrowable(() -> new Reservation(client, car, 0));

    Assertions.assertThat(error)
        .isInstanceOf(ReservationInvalidException.class)
        .hasMessage("The number of days cannot be 0 or less than 0");
  }

  @Test
  @DisplayName("Should calculate the total rent")
  void shouldCalculateTotalRent() throws Exception {
    Reservation reservation = new Reservation(client, car, 3);
    double total = reservation.calculateTotalReservation(3);

    assertEquals(300.0, total);
  }
}

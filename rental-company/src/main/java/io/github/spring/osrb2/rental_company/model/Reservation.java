package io.github.spring.osrb2.rental_company.model;

import io.github.spring.osrb2.rental_company.model.exception.ReservationInvalidException;

public class Reservation {
  private Client client;
  private Car car;
  private int numberDays;

  public Reservation(Client client, Car car, int numberDays) {
    if (numberDays < 1) {
      throw new ReservationInvalidException("The number of days cannot be 0 or less than 0");
    }
    this.client = client;
    this.car = car;
    this.numberDays = numberDays;
  }

  public double calculateTotalReservation(int days) throws Exception {
    return car.calculateRentValue(days);
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public int getNumberDays() {
    return numberDays;
  }

  public void setNumberDays(int numberDays) {
    this.numberDays = numberDays;
  }
}

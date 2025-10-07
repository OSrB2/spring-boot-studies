package io.github.spring.osrb2.rental_company.model;

public class Car {
  private String model;
  private double dailyValue;

  public Car(String model, Double dailyValue) {
    this.model = model;
    this.dailyValue = dailyValue;
  }

  public double calculateRentValue(int days) {
    double amount = 0;
    if (days >= 5) {
      amount = 50.0;
    }
    return (days * dailyValue) - amount;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Double getDailyValue() {
    return dailyValue;
  }

  public void setDailyValue(Double dailyValue) {
    this.dailyValue = dailyValue;
  }
}

package io.github.spring.osrb2.rental_company.controller;

import io.github.spring.osrb2.rental_company.entity.CarEntity;
import io.github.spring.osrb2.rental_company.model.exception.EntityNotFoundException;
import io.github.spring.osrb2.rental_company.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarControllerTest {

  @Autowired
  MockMvc mvc;

  @MockitoBean
  CarService service;

  CarEntity car1;
  CarEntity car2;
  CarEntity carUpdate;

  @BeforeEach
  void config() {
    car1 = new CarEntity();
    car1.setId(1L);
    car1.setModel("Sedan");
    car1.setDailyValue(50.0);
    car1.setCarYear(2023);

    car2 = new CarEntity();
    car2.setId(2L);
    car2.setModel("Hatch");
    car2.setDailyValue(150.0);
    car2.setCarYear(2022);

    carUpdate = new CarEntity();
    carUpdate.setId(2L);
    carUpdate.setModel("Hatch");
    carUpdate.setDailyValue(180.0);
    carUpdate.setCarYear(2022);
  }

  @Test
  @DisplayName("Should save a new car")
  void shouldSaveCar() throws Exception {
    // Cenário
    when(service.save(any())).thenReturn(car1);

    String json = """
        {
          "model": "Sedan",
          "dailyValue": 50.0,
          "carYear": 2023
        }
        """;
    // Execução
    ResultActions result = mvc.perform(post("/api/cars")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    // Verificação
    result.andExpect(status().isCreated())
          .andExpect(jsonPath("$.id").value(1))
          .andExpect(jsonPath("$.model").value("Sedan"))
          .andExpect(jsonPath("$.dailyValue").value(50.0))
          .andExpect(jsonPath("$.carYear").value(2023));
  }

  @Test
  @DisplayName("Should find car by ID")
  void shouldFindCarById() throws Exception {
    when(service.findById(any())).thenReturn(car1);

    mvc.perform(get("/api/cars/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.model").value("Sedan"))
        .andExpect(jsonPath("$.dailyValue").value(50.0))
        .andExpect(jsonPath("$.carYear").value(2023));
  }

  @Test
  @DisplayName("Should return not found if the car does not exist")
  void shouldReturnNotFoundIfCarNotExist() throws Exception {
    when(service.findById(any())).thenThrow(EntityNotFoundException.class);

    mvc.perform(get("/api/cars/{id}", 1))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should list all cars")
  void shouldListAllCars() throws Exception {
    var list = List.of(car1, car2);
    when(service.listAll()).thenReturn(list);

    mvc.perform(get("/api/cars", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].model").value("Sedan"))
        .andExpect(jsonPath("$[0].dailyValue").value(50.0))
        .andExpect(jsonPath("$[0].carYear").value(2023))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].model").value("Hatch"))
        .andExpect(jsonPath("$[1].dailyValue").value(150.0))
        .andExpect(jsonPath("$[1].carYear").value(2022));
  }

  @Test
  @DisplayName("Should update or edit car by id")
  void shouldUpdateEditCar() throws Exception {
    when(service.update(any(), any())).thenReturn(carUpdate);

    String json = """
        {
          "model": "Hatch",
          "dailyValue": 180.0,
          "carYear": 2022
        }
        """;

    mvc.perform(put("/api/cars/{id}", 2)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Should return not found when trying to update if the car does not exist")
  void shouldReturnNotFoundWhenUpdateIfCarNotExist() throws Exception {
    when(service.update(any(), any())).thenThrow(EntityNotFoundException.class);

    String json = """
        {
          "model": "Hatch",
          "dailyValue": 180.0,
          "carYear": 2022
        }
        """;

    mvc.perform(put("/api/cars/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should delete car by ID")
  void shouldDeleteCarById() throws Exception {
    doNothing().when(service).delete(any());

    mvc.perform(delete("/api/cars/{id}", 1))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Should return not found when trying to delete if the car does not exist")
  void shouldReturnNotFoundWhenTryDeleteIfCarNotExist() throws Exception {
    doThrow(EntityNotFoundException.class).when(service).delete(any());

    mvc.perform(delete("/api/cars/{id}", 1))
        .andExpect(status().isNotFound());
  }
}
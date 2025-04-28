package io.github.spring_boot_studies.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserDTO(
    @NotBlank(message = "Is mandatory")
    String login,
    @NotBlank(message = "Is mandatory")
    String password,
    @Email(message = "Is invalid")
    @NotBlank(message = "Is mandatory")
    String email,
    List<String> roles) {
}

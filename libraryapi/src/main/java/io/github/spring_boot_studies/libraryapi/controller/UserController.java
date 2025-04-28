package io.github.spring_boot_studies.libraryapi.controller;

import io.github.spring_boot_studies.libraryapi.controller.dto.UserDTO;
import io.github.spring_boot_studies.libraryapi.controller.mappers.UserMapper;
import io.github.spring_boot_studies.libraryapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@RequestBody @Valid UserDTO userDTO) {
    var user = userMapper.toEntity(userDTO);
    userService.registerUser(user);
  }
}

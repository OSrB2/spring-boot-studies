package io.github.spring_boot_studies.libraryapi.service;

import io.github.spring_boot_studies.libraryapi.model.User;
import io.github.spring_boot_studies.libraryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void registerUser(User user) {
    var pass = user.getPassword();
    user.setPassword(passwordEncoder.encode(pass)); // Criptografa a senha
    userRepository.save(user);
  }

  public User getByLogin(String login) {
    return userRepository.findByLogin(login);
  }

  public User getByEmail(String email) {
    return userRepository.findByEmail(email);
  }

}

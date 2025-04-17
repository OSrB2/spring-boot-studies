package io.github.spring_boot_studies.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Habilita a auditoria do Spring Data JPA, funciona com a anotação @CreatedDate e @LastModifiedDate
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



}

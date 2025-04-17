package io.github.spring_boot_studies.libraryapi.controller.dto;

// Record é uma classe que é imutável, ou seja, não pode ser alterada depois de criada.
// Ele é uma classe que tem um construtor, getters e toString() gerados automaticamente.

// Esse record é utilizado para o campo que falhou na validação e retorna o campo e a mensagem de erro.

public record FieldErrorImpl(String field, String message) {

}

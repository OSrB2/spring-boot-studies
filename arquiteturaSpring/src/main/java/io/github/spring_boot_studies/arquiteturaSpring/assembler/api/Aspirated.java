package io.github.spring_boot_studies.arquiteturaSpring.assembler.api;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Essa anotação serve para marcar campos ou métodos que representam um motor aspirado.
// Essa anotação pode ser usada em conjunto com o @Qualifier para injetar o bean correto.

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Qualifier("aspiratedEngine")
public @interface Aspirated {
}

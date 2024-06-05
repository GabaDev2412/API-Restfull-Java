package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

// record é uma classe que serve para armazenar dados de forma imutável
// e é uma classe final por padrão, ou seja, não pode ser estendida
// @NotBlank é uma anotação de validação que define que o atributo não pode ser nulo ou vazio
// @NotNull é uma anotação de validação que define que o atributo não pode ser nulo
public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value) {
}

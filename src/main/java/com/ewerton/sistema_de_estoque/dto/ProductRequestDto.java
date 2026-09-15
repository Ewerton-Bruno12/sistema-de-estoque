package com.ewerton.sistema_de_estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDto(
        @NotBlank(message = "O nome do produto é obrigatório")
        String name,
        String description,
        @NotNull(message = "O preço do produto é obrigatório")
        @Positive(message = "O preço do produto deve ser maior que 0")
        BigDecimal price,
        @NotNull(message = "O ID da categoria é obrigatório")
        Long categoryId
) {}

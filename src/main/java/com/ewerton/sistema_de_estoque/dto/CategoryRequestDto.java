package com.ewerton.sistema_de_estoque.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(
        @NotBlank(message = "O nome da categoria é obrigatório")
        String name
) {}

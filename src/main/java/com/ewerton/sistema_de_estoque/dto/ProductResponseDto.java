package com.ewerton.sistema_de_estoque.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        String categoryName,
        LocalDateTime createdAt
) {}

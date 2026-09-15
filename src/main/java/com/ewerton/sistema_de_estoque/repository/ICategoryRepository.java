package com.ewerton.sistema_de_estoque.repository;

import com.ewerton.sistema_de_estoque.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);
}

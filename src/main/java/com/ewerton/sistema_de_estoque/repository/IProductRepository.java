package com.ewerton.sistema_de_estoque.repository;

import com.ewerton.sistema_de_estoque.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByCategoryId(Long categoryId);
}

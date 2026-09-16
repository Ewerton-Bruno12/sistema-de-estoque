package com.ewerton.sistema_de_estoque.service;

import com.ewerton.sistema_de_estoque.dto.ProductRequestDto;
import com.ewerton.sistema_de_estoque.dto.ProductResponseDto;
import com.ewerton.sistema_de_estoque.exception.InsufficientStockException;
import com.ewerton.sistema_de_estoque.exception.ResourceNotFoundException;
import com.ewerton.sistema_de_estoque.model.CategoryEntity;
import com.ewerton.sistema_de_estoque.model.ProductEntity;
import com.ewerton.sistema_de_estoque.repository.ICategoryRepository;
import com.ewerton.sistema_de_estoque.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long id) {
        return productRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Produto com o ID " + id + " não foi encontrado."));
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findByCategoryId(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com o ID: " + id);
        }

        return productRepository.findByCategoryId(id)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public ProductResponseDto save(ProductRequestDto productRequestDto) {
        CategoryEntity category = categoryRepository.findById(productRequestDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria não encontrada com o ID: " + productRequestDto.categoryId()));

        ProductEntity newProduct = ProductEntity.builder()
                .name(productRequestDto.name())
                .description(productRequestDto.description())
                .price(productRequestDto.price())
                .quantity(0)
                .category(category)
                .build();

        ProductEntity savedProduct = productRepository.save(newProduct);
        return toDto(savedProduct);
    }

    @Transactional
    public ProductResponseDto update(Long id, ProductRequestDto productRequestDto) {
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Produto não encontrado."));

        CategoryEntity category = categoryRepository.findById(productRequestDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria não encontrada com o ID: " + productRequestDto.categoryId()));

        existingProduct.setName(productRequestDto.name());
        existingProduct.setDescription(productRequestDto.description());
        existingProduct.setPrice(productRequestDto.price());
        existingProduct.setCategory(category);

        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return toDto(updatedProduct);
    }

    @Transactional
    public void delete(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        productRepository.delete(productEntity);
    }

    @Transactional
    public ProductResponseDto addStock(Long id, Integer quantity) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        productEntity.setQuantity(productEntity.getQuantity() + quantity);

        ProductEntity updatedProduct = productRepository.save(productEntity);
        return toDto(updatedProduct);
    }

    @Transactional
    public ProductResponseDto removeStock(Long id, Integer quantity) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        if (quantity > productEntity.getQuantity()) {
            throw new InsufficientStockException(
                    "Estoque insuficiente para o produto '" + productEntity.getName() +
                    "'. Disponível: " + productEntity.getQuantity() + ", Solicitado: " + quantity);
        }

        productEntity.setQuantity(productEntity.getQuantity() - quantity);

        ProductEntity updatedProduct = productRepository.save(productEntity);
        return toDto(updatedProduct);
    }

    private ProductResponseDto toDto(ProductEntity product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory().getName(),
                product.getCreatedAt()
        );
    }
}

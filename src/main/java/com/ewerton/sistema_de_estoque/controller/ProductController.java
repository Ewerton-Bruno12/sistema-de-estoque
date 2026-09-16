package com.ewerton.sistema_de_estoque.controller;

import com.ewerton.sistema_de_estoque.dto.ProductRequestDto;
import com.ewerton.sistema_de_estoque.dto.ProductResponseDto;
import com.ewerton.sistema_de_estoque.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/products")
@RequiredArgsConstructor
@Validated
public class ProductControler {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.save(productRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> findAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto findByIdProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> findByIdCategoryProduct(@PathVariable Long id) {
        return productService.findByCategoryId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.update(id, productRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }
}

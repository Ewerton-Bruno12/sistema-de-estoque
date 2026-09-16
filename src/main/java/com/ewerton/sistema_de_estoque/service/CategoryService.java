package com.ewerton.sistema_de_estoque.service;

import com.ewerton.sistema_de_estoque.dto.CategoryRequestDto;
import com.ewerton.sistema_de_estoque.dto.CategoryResponseDto;
import com.ewerton.sistema_de_estoque.exception.CategoryNameAlreadyExistsException;
import com.ewerton.sistema_de_estoque.exception.CategoryNotEmptyException;
import com.ewerton.sistema_de_estoque.exception.ResourceNotFoundException;
import com.ewerton.sistema_de_estoque.model.CategoryEntity;
import com.ewerton.sistema_de_estoque.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ICategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria com o ID " + id + " não foi encontrada"));
    }

    @Transactional
    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        if (categoryRepository.existsByName(categoryRequestDto.name())) {
            throw new CategoryNameAlreadyExistsException(
                    "A categoria '" + categoryRequestDto.name() + "' já está cadastrada.");
        }

        CategoryEntity newCategory = CategoryEntity.builder()
                .name(categoryRequestDto.name())
                .build();

        CategoryEntity savedCategory = categoryRepository.save(newCategory);
        return toDto(savedCategory);
    }

    @Transactional
    public CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto) {
        CategoryEntity existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        if (!existingCategory.getName().equals(categoryRequestDto.name())
                 && categoryRepository.existsByName(categoryRequestDto.name())) {
            throw new CategoryNameAlreadyExistsException("O nome da categoria já está cadastrada.");
        }

        existingCategory.setName(categoryRequestDto.name());

        CategoryEntity updatedCategory = categoryRepository.save(existingCategory);
        return toDto(updatedCategory);
    }

    @Transactional
    public void delete(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada."));

        if (categoryEntity.getProducts() != null && !categoryEntity.getProducts().isEmpty()) {
            throw new CategoryNotEmptyException(
                    "Não é possível deletar a categoria '" + categoryEntity.getName() +
                    "' porque ela ainda possui produtos vinculados no estoque");
        }

        categoryRepository.delete(categoryEntity);
    }

    private CategoryResponseDto toDto(CategoryEntity category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }

}

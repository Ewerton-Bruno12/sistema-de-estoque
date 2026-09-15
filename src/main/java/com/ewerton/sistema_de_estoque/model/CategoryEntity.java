package com.ewerton.sistema_de_estoque.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProductEntity> products;


}

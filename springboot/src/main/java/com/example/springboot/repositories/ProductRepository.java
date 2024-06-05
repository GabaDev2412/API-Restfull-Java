package com.example.springboot.repositories;

import com.example.springboot.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository // @Repository define que a classe é um repositório (interage com o banco de dados)
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
}

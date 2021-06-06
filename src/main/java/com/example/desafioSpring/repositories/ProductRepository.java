package com.example.desafioSpring.repositories;

import com.example.desafioSpring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}

package com.github.aursu.hosting.webapp.repositories;

import com.github.aursu.hosting.webapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findPackages();
    List<Product> findServices();
}
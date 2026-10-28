package com.levarvizu.fullstacklab.repository;

import com.levarvizu.fullstacklab.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
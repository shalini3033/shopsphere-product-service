package com.shopsphere.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopsphere.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}

package com.example.appsecurityfirst.repository;


import com.example.appsecurityfirst.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}

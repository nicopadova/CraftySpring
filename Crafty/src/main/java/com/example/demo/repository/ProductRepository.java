package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
	
	List<Product> findByNome(String nome);
	
	List<Product> findByNomeContaining(String nomePart);

}

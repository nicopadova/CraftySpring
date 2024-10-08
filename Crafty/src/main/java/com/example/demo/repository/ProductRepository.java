package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
	
	List<Product> findByNome(String nome);
	
	List<Product> findByNomeContainingOrDescrizioneContaining(String nome, String descrizione);

}

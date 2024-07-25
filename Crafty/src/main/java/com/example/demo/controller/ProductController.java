package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping
	public List<Product> getAllProduct(){
		return productRepository.findAll();
	}
	
	@PostMapping
	public Product createProduct(@RequestBody Product product){
		return productRepository.save(product);
	}
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Prodotto non trovato"));
	}
	
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Prodotto non trovato"));
		product.setNome(productDetails.getNome());
		product.setCategory(productDetails.getCategory());
		product.setDescrizione(productDetails.getDescrizione());
		product.setPrezzo(productDetails.getPrezzo());
		product.setQnt(productDetails.getQnt());
		return productRepository.save(product);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Prodotto non trovato"));
		productRepository.delete(product);
	}
	
	@GetMapping("/searchByNome")
	public List<Product> getProductByNome(@RequestParam String nome){
		return productRepository.findByNome(nome);
	}
	
	@GetMapping("/searchByNomePart")
	public List<Product> getProductByNomeContaining(@RequestParam String nomePart){
		return productRepository.findByNomeContaining(nomePart);
	}
	
	
	
	
}


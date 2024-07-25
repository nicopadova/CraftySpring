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

import com.example.demo.repository.CategoryRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Category;
@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}
	
	@PostMapping
	public Category createCategory(@RequestBody Category category){
		return categoryRepository.save(category);
	}
	
	@GetMapping("/{id}")
	public Category getCategoryById(@PathVariable Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Destinazione non trovata"));
	}
	
	@PutMapping("/{id}")
	public Category updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Destinazione non trovata"));
		category.setNome(categoryDetails.getNome());
		return categoryRepository.save(category);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCategory(@PathVariable Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Destinazione non trovata"));
		categoryRepository.delete(category);
	}
	
	@GetMapping("/searchByNome")
	public List<Category> getCategoryByNome(@RequestParam String nome){
		return categoryRepository.findByNome(nome);
	}
	
	
	
	
}

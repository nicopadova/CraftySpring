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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.Category;
import com.example.demo.model.Token;
import com.example.demo.model.User;
@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}
	
	//riservato admin
	@PostMapping
	public Category createCategory(@RequestBody Category category, @RequestHeader("Authorization") String token){
		Token authToken = tokenService.findByToken(token);
	       if (authToken != null) {
	    	   User user = userService.getUserById(authToken.getUserId());
	           if ("ADMIN".equals(user.getRole())) { 
	        	   return categoryRepository.save(category);
	           } else {
	               throw new UnauthorizedException();
	           }
	       } else {
	       	  throw new UnauthorizedException();
	       }	        	   
	}
	
	@GetMapping("/{id}")
	public Category getCategoryById(@PathVariable Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Categoria non trovata"));
	}
	
	//per l'admin
	@PutMapping("/{id}")
	public Category updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails, @RequestHeader("Authorization") String token) {
		Token authToken = tokenService.findByToken(token);
	       if (authToken != null) {
	    	   User user = userService.getUserById(authToken.getUserId());
	           if ("ADMIN".equals(user.getRole())) { 
				Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Categoria non trovata"));
				category.setNome(categoryDetails.getNome());
				return categoryRepository.save(category);
	           } else {
	               throw new UnauthorizedException();
	           }
	       } else {
	       	  throw new UnauthorizedException();
	       }
	}
	
	@DeleteMapping("/{id}")
	public void deleteCategory(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		Token authToken = tokenService.findByToken(token);
	       if (authToken != null) {
	    	   User user = userService.getUserById(authToken.getUserId());
	           if ("ADMIN".equals(user.getRole())) { 
	        	   Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Categoria non trovata"));
	        	   categoryRepository.delete(category);
	           } else {
	               throw new UnauthorizedException();
	           }
	       } else {
	       	  throw new UnauthorizedException();
	       }
	}
	
	@GetMapping("/searchByNome")
	public List<Category> getCategoryByNome(@RequestParam String nome){
		return categoryRepository.findByNome(nome);
	}
	
	
	
	
}

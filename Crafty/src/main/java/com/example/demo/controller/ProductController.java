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

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.Product;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private ProductRepository productRepository;
	
	
	@GetMapping
	public List<Product> getAllProduct(){
		return productRepository.findAll();
	}
	
	//riservato all'admin
	@PostMapping
	public Product createProduct(@RequestBody Product product, @RequestHeader("Authorization") String token){
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
            User user = userService.getUserById(authToken.getUserId());
            if ("ADMIN".equals(user.getRole())) {
		return productRepository.save(product);
            } else {
             throw new UnauthorizedException();
         }
     } else {
         throw new UnauthorizedException();
     }
	}
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Prodotto non trovato"));
	}
	
	//riservato all'admin
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails, @RequestHeader("Authorization") String token) {
		Token authToken = tokenService.findByToken(token);
	       if (authToken != null) {
	    	   User user = userService.getUserById(authToken.getUserId());
	           if ("ADMIN".equals(user.getRole())) { 
					Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Prodotto non trovato"));
					product.setNome(productDetails.getNome());
					product.setCategory(productDetails.getCategory());
					product.setDescrizione(productDetails.getDescrizione());
					product.setPrezzo(productDetails.getPrezzo());
					product.setQnt(productDetails.getQnt());
					product.setImg(productDetails.getImg());
					product.setTag(productDetails.getTag());
					product.setVenditore(productDetails.getVenditore());
					return productRepository.save(product);
		           } else {
		               throw new UnauthorizedException();
		           }
		       } else {
		       	  throw new UnauthorizedException();
		       }
	}
	
	//riservato all'admin 
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		 Token authToken = tokenService.findByToken(token);
	       if (authToken != null) {
	    	   User user = userService.getUserById(authToken.getUserId());
	           if ("ADMIN".equals(user.getRole())) {
				Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Prodotto non trovato"));
				productRepository.delete(product);
	           } else {
	               throw new UnauthorizedException();
	           }
	       } else {
	       	  throw new UnauthorizedException();
	       }			
	}
	
	@GetMapping("/searchByNome")
	public List<Product> getProductByNome(@RequestParam String nome){
		return productRepository.findByNome(nome);
	}
	
	
	@GetMapping("/searchProducts")
	public List <Product> searchProducts (@RequestParam String searchTerm){
		return productRepository.findByNomeContainingOrDescrizioneContaining(searchTerm, searchTerm);
	}
	
}


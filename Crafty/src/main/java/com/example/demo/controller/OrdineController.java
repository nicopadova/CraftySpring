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
import com.example.demo.model.Ordine;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.repository.OrdineRepository;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/ordine")
public class OrdineController {
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private OrdineRepository ordineRepository;
	
	//riservato a admin
	@GetMapping
	public List<Ordine> getAllOrdine(@RequestHeader("Authorization") String token){
		 Token authToken = tokenService.findByToken(token);
	       if (authToken != null) {
	    	   User user = userService.getUserById(authToken.getUserId());
	           if ("ADMIN".equals(user.getRole())) { 
				return ordineRepository.findAll();
	           } else {
	               throw new UnauthorizedException();
	           }
	       } else {
	       	  throw new UnauthorizedException();
	       }
	}
	
	//per utente loggato
	@PostMapping
	public Ordine createOrdine(@RequestBody Ordine ordine, @RequestHeader("Authorization") String token){
		System.out.println("token:" + token);
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
		return ordineRepository.save(ordine);
		} else {
			throw new UnauthorizedException();
		}
	}
	
	//per l'utente Loggato
	@GetMapping("/{id}")
	public Ordine getOrdineById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		System.out.println("token:" + token);
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
		return ordineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Ordine non trovato"));
		} else {
			throw new UnauthorizedException();
		}
	}
	//riservato all'admin
	@PutMapping("/{id}")
	public Ordine updateOrdine(@PathVariable Long id, @RequestBody Ordine ordineDetails,@RequestHeader("Authorization") String token) {
		Token authToken = tokenService.findByToken(token);
	       if (authToken != null) {
	    	   User user = userService.getUserById(authToken.getUserId());
	           if ("ADMIN".equals(user.getRole())) { 
				Ordine ordine = ordineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Ordine non trovato"));
				ordine.setData(ordineDetails.getData());
				ordine.setStato(ordineDetails.getStato());
				return ordineRepository.save(ordine);
	           } else {
	               throw new UnauthorizedException();
	           }
	       } else {
	       	  throw new UnauthorizedException();
	       }			
	}
	//riservato all'admin
	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable Long id,@RequestHeader("Authorization") String token) {
		Token authToken = tokenService.findByToken(token);
	       if (authToken != null) {
	    	   User user = userService.getUserById(authToken.getUserId());
	           if ("ADMIN".equals(user.getRole())) { 
				Ordine ordine = ordineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Ordine non trovato"));
				ordineRepository.delete(ordine);
	           } else {
	               throw new UnauthorizedException();
	           }
	       } else {
	       	  throw new UnauthorizedException();
	       }
	}
	
	//per l'utente loggato
	
	@GetMapping("/searchByUserId")
	public List<Ordine> getOrderByUserId(@RequestParam Long user_id, @RequestHeader("Authorization") String token){
		System.out.println("token:" + token);
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
		return ordineRepository.findByUserId(user_id);
		} else {
			throw new UnauthorizedException();
		}
	}
}

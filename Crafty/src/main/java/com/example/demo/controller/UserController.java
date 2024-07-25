package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping
	public List <User> getAllUser() {
		return userRepo.findAll ();
	}
	
	@PostMapping
	public User crateUser (User user) {
		return userRepo.save(user);
	}
	
	@GetMapping("/{id}")
	public User getUserById(Long id){
		return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Utente non trovato"));
	}
	
	@PutMapping("/{id}")
	public User updateUser(Long id, User userDetails) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Utente non trovato"));
		user.setNome(userDetails.getNome());
		user.setCognome(userDetails.getCognome());
		user.setEmail(userDetails.getEmail());
		user.setPassword(userDetails.getPassword());
		user.setRole(userDetails.getRole());
		return userRepo.save(user);
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Utente non trovato"));
		userRepo.delete(user);
	}
	
	@GetMapping("/searchByEmail")
	public User getUserByEmail(String email){
		return userRepo.findByEmail(email);
	}
	
	@GetMapping("/searchByEmailAndPassword")
	public List <User> getUserByEmailAndPassword(String email, String password) {
		return userRepo.findByEmailAndPassword(email, password);
	}
	
	

}

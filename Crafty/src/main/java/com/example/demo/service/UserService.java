package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public User getByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	public List <User> getAllUser() {
		return userRepo.findAll ();
	}
	public User crateUser (User user) {
		return userRepo.save(user);
	}
	public User getUserById(Long id){
		return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Utente non trovato"));
	}
	public User updateUser(Long id, User userDetails) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Utente non trovato"));
		user.setNome(userDetails.getNome());
		user.setCognome(userDetails.getCognome());
		user.setEmail(userDetails.getEmail());
		user.setPassword(userDetails.getPassword());
		user.setRole(userDetails.getRole());
		return userRepo.save(user);
	}
	
	public void deleteUser(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Utente non trovato"));
		userRepo.delete(user);
	}
	
	public User getUserByEmail(String email){
		return userRepo.findByEmail(email);
	}
	
	public List <User> getUserByEmailAndPassword(String email, String password) {
		return userRepo.findByEmailAndPassword(email, password);
	}
}
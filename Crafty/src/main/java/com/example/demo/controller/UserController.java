package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	   // Endpoint per ottenere i dati dell'utente loggato
    @GetMapping("/me")
    public User getUserData(@RequestHeader("Authorization") String token) {
    	System.out.println("token:" + token);
    	 // Trova il token nel database
        Token authToken = tokenService.findByToken(token);
        
        // Se il token è valido, restituisce i dati dell'utente
        if (authToken != null) {
            return userService.getUserById(authToken.getUserId());
        } else {
        	// Se il token non è valido, lancia un'eccezione di non autorizzato
            throw new UnauthorizedException();
        }
    }
    

    // Endpoint riservato all'admin per ottenere la lista di tutti gli utenti
    @GetMapping("/users")
    public List<User> getAllUsers(@RequestHeader("Authorization") String token) {
    	   // Trova il token nel database
    	Token authToken = tokenService.findByToken(token);
    	  // Se il token è valido, verifica il ruolo dell'utente
        if (authToken != null) {
            User user = userService.getUserById(authToken.getUserId());
            // Se l'utente ha il ruolo di admin, restituisce la lista di tutti gli utenti
            if ("ADMIN".equals(user.getRole())) {
                return userService.getAllUser();
            } else {
            	   // Se l'utente non è admin, lancia un'eccezione di non autorizzato
                throw new UnauthorizedException();
            }
        } else {
        	 // Se il token non è valido, lancia un'eccezione di non autorizzato
            throw new UnauthorizedException();
        }
    }
	
	@PostMapping
	public User crateUser (@RequestBody User user) {
		return userService.crateUser(user);
	}
	
	@GetMapping("/{id}")
	public User getUserById(@RequestHeader("Authorization") String token,@PathVariable Long id){
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
            User user = userService.getUserById(authToken.getUserId());
            if ("ADMIN".equals(user.getRole())) {
            	return userService.getUserById(id);
            } else {
                throw new UnauthorizedException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }
            
            
	
	
	@PutMapping("/{id}")
	public User updateUser(@PathVariable  Long id,@RequestBody User userDetails,@RequestHeader("Authorization") String token) {
		System.out.println("token:" + token);
		 // Trova il token nel database
        Token authToken = tokenService.findByToken(token);
        // Se il token è valido, restituisce i dati dell'utente
        if (authToken != null) {
		User user = userService.getUserById(id);
		user.setNome(userDetails.getNome());
		user.setCognome(userDetails.getCognome());
		user.setEmail(userDetails.getEmail());
		user.setPassword(userDetails.getPassword());
		user.setRole(userDetails.getRole());
		user.setCarrello(userDetails.getCarrello());
		user.setTelefono(userDetails.getTelefono());
		user.setIndirizzo(userDetails.getIndirizzo());
		return userService.crateUser(user);
        } else {
        	 throw new UnauthorizedException();
        }
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		System.out.println("token:" + token);
		 Token authToken = tokenService.findByToken(token);
		 if (authToken != null) {		
		userService.deleteUser(id);
		 } else {
			 throw new UnauthorizedException();
		 }
	}
	
	@GetMapping("/searchByEmail")
	public User getUserByEmail(@RequestParam String email){
		return userService.getUserByEmail(email);
	}
	
	@GetMapping("/login")
	public List <User> getUserByEmailAndPassword(@RequestParam String email,@RequestParam  String password) {
		return userService.getUserByEmailAndPassword(email, password);
	}
	
	@GetMapping("/admin")
	public ResponseEntity<String> getAccess(@RequestHeader("Authorization") String token) {
		Token authToken = tokenService.findByToken(token);
		if (authToken!=null) {
			User user = userService.getUserById(authToken.getUserId());
			if (user!=null&&"ADMIN".equals(user.getRole())) {
				ResponseEntity<String> risposta = null;
				ResponseEntity.status(HttpStatus.OK);
				return risposta;
			} else {
				throw new UnauthorizedException();
			}
		} else {
			throw new UnauthorizedException();
		}
	}

}

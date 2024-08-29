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

import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.Review;
import com.example.demo.model.Token;
import com.example.demo.model.User;

@CrossOrigin
@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	ReviewRepository reviewRepository;
	
	@GetMapping
	public List <Review> getAllReview() {
		return reviewRepository.findAll();
		 
	}
	// per creare una nuova recensione devi avere fatto l'accesso
	@PostMapping("/{id}")
	public Review createReview(@RequestBody Review review,@RequestHeader("Authorization") String token,@PathVariable Long id) {
		Token authToken = tokenService.findByToken(token);
		 if (authToken != null) {
		return reviewRepository.save(review);
		 } else {
	        	// Se il token non è valido, lancia un'eccezione di non autorizzato
	            throw new UnauthorizedException();
	        }
	}
	
	//riservato a utente loggato
	@GetMapping("/{id}")
	public Review getReviewById (@PathVariable Long id, @RequestBody Review reviewDetails,@RequestHeader("Authorization") String token){
		System.out.println("token:" + token);
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
		return reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Recensione non trovata"));
		} else {
            throw new UnauthorizedException();
        }
	}
	
	//gli utenti possono modificare la recensione
	@PutMapping("/{id}")
	public Review updateReview(@PathVariable Long id, @RequestBody Review reviewDetails,@RequestHeader("Authorization") String token) {
		System.out.println("token:" + token);
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
		Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Recensione non trovata"));
		review.setDescrizione(reviewDetails.getDescrizione());
		review.setPunteggio(reviewDetails.getPunteggio());
		return reviewRepository.save(review);
		} else {
			throw new UnauthorizedException();
		}
	}
	
	//riservato all'admin
	@DeleteMapping("/{id}")
	public void deleteReview(@PathVariable Long id, @RequestHeader("Authorization") String token) {
       Token authToken = tokenService.findByToken(token);
       if (authToken != null) {
    	   User user = userService.getUserById(authToken.getUserId());
           if ("ADMIN".equals(user.getRole())) { 
				Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Recensione non trovata"));
				reviewRepository.delete(review);
           } else {
            throw new UnauthorizedException();
        }
    } else {
    	  throw new UnauthorizedException();
    }
					
	}
	
	@GetMapping("/searchByUserId")
	public List<Review> getReviewByUserId(@RequestParam Long user_id){
		return reviewRepository.findByUserId(user_id);
	}
	
	@GetMapping("/searchByPunteggio")
	public List<Review> getReviewByPunteggio(@RequestParam Integer punteggio){
		return reviewRepository.findByPunteggio(punteggio);
	}
	
	@GetMapping("/searchByProductId")
	public List<Review> getReviewByProductId(@RequestParam Long product_id){
		return reviewRepository.findByProductId(product_id);
	}
	
}

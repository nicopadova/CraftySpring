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

import com.example.demo.repository.ReviewRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Review;

@CrossOrigin
@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@GetMapping
	public List <Review> getAllReview() {
		return reviewRepository.findAll();
	}
	
	@PostMapping
	public Review createReview(@RequestBody Review review) {
		return reviewRepository.save(review);
	}
	
	@GetMapping("/{id}")
	public Review getReviewById (@PathVariable Long id){
		return reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Recensione non trovata"));
	}
	
	@PutMapping("/{id}")
	public Review updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
		Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Recensione non trovata"));
		review.setDescrizione(reviewDetails.getDescrizione());
		review.setPunteggio(reviewDetails.getPunteggio());
		return reviewRepository.save(review);
	}
	
	@DeleteMapping("/{id}")
	public void deleteReview(@PathVariable Long id) {
		Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Recensione non trovata"));
		reviewRepository.delete(review);
	}
	
	@GetMapping("/searchByUserId")
	public List<Review> getReviewByUserId(@RequestParam Long user_id){
		return reviewRepository.findByUserId(user_id);
	}
	
	@GetMapping("/searchByPunteggio")
	public List<Review> getReviewByPunteggio(@RequestParam Integer punteggio){
		return reviewRepository.findByPunteggio(punteggio);
	}
	

}

package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Review;


public interface ReviewRepository extends JpaRepository<Review,Long> {
	
	List <Review> findByPunteggio(Integer punteggio);
	
	List <Review> findByUserId(Long user_id);

}

package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository <OrderDetails, Long>{
	
	List <OrderDetails> findByUserId(Long user_id);
	
	List <OrderDetails> findByOrdineId(Long ordine_id);
	
}
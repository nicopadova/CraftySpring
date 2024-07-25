package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Ordine;


public interface OrdineRepository extends JpaRepository <Ordine, Long>{
	
	List <Ordine> findByUserId(Long user_id);

}

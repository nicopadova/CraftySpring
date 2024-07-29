package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Ordine;

@Repository
public interface OrdineRepository extends JpaRepository <Ordine, Long>{
	
	List <Ordine> findByUserId(Long user_id);

}

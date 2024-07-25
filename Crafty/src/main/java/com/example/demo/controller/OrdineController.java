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

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Ordine;
import com.example.demo.repository.OrdineRepository;

@CrossOrigin
@RestController
@RequestMapping("/ordine")
public class OrdineController {
	@Autowired
	private OrdineRepository ordineRepository;
	
	@GetMapping
	public List<Ordine> getAllOrdine(){
		return ordineRepository.findAll();
	}
	
	@PostMapping
	public Ordine createOrdine(@RequestBody Ordine ordine){
		return ordineRepository.save(ordine);
	}
	
	@GetMapping("/{id}")
	public Ordine getOrdineById(@PathVariable Long id) {
		return ordineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Ordine non trovato"));
	}
	
	@PutMapping("/{id}")
	public Ordine updateOrdine(@PathVariable Long id, @RequestBody Ordine ordineDetails) {
		Ordine ordine = ordineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Ordine non trovato"));
		ordine.setData(ordineDetails.getData());
		return ordineRepository.save(ordine);
	}
	
	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable Long id) {
		Ordine ordine = ordineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Ordine non trovato"));
		ordineRepository.delete(ordine);
	}
	
	@GetMapping("/searchByUserId")
	public List<Ordine> getOrderByUserId(@RequestParam Long user_id){
		return ordineRepository.findByUserId(user_id);
	}
}

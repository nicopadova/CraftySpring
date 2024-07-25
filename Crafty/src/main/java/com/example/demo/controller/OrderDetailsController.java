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
import com.example.demo.model.OrderDetails;
import com.example.demo.repository.OrderDetailsRepository;

@CrossOrigin
@RestController
@RequestMapping("/orderDetails")
public class OrderDetailsController {
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	@GetMapping
	public List<OrderDetails> getAllOrderDetails(){
		return orderDetailsRepository.findAll();
	}
	
	@PostMapping
	public OrderDetails createOrderDetails(@RequestBody OrderDetails orderDetails){
		return orderDetailsRepository.save(orderDetails);
	}
	
	@GetMapping("/{id}")
	public OrderDetails getOrderDetailsById(@PathVariable Long id) {
		return orderDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Destinazione non trovata"));
	}
	
	@PutMapping("/{id}")
	public OrderDetails updateOrderDetails(@PathVariable Long id, @RequestBody OrderDetails orderDetailsDetails) {
		OrderDetails orderDetails = orderDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Destinazione non trovata"));
		orderDetails.setPrezzo(orderDetailsDetails.getPrezzo());
		orderDetails.setQnt(orderDetailsDetails.getQnt());
		orderDetails.setProduct(orderDetailsDetails.getProduct());
		return orderDetailsRepository.save(orderDetails);
	}
	
	@DeleteMapping("/{id}")
	public void deleteOrderDetails(@PathVariable Long id) {
		OrderDetails orderDetails = orderDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Destinazione non trovata"));
		orderDetailsRepository.delete(orderDetails);
	}
	
	@GetMapping("/searchByUserId")
	public List<OrderDetails> getOrderDetailsByUserId(@RequestParam Long user_id){
		return orderDetailsRepository.findByUserId(user_id);
	}
	
	@GetMapping("/searchByOrderId")
	public List<OrderDetails> getOrderDetailsByOrderId(@RequestParam Long ordine_id){
		return orderDetailsRepository.findByOrdineId(ordine_id);
	}
	

}

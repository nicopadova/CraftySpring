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

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.repository.OrderDetailsRepository;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/orderDetails")
public class OrderDetailsController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	
	
	//per l'utente loggato
	@PostMapping
	public OrderDetails createOrderDetails(@RequestBody OrderDetails orderDetails, @RequestHeader("Authorization") String token){
		Token authToken = tokenService.findByToken(token);
		 if (authToken != null) {
		return orderDetailsRepository.save(orderDetails);
		 } else {
	            throw new UnauthorizedException();
	        }
	}
	
	@GetMapping("/{id}")
	public OrderDetails getOrderDetailsById(@PathVariable Long id) {
		return orderDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException (" non trovata"));
	} 
	
	//per l'admin
	@PutMapping("/{id}")
	public OrderDetails updateOrderDetails(@PathVariable Long id, @RequestBody OrderDetails orderDetailsDetails,  @RequestHeader("Authorization") String token) {
		 Token authToken = tokenService.findByToken(token);
		 if (authToken != null) {
	    	   User user = userService.getUserById(authToken.getUserId());
	           if ("ADMIN".equals(user.getRole())) { 
				OrderDetails orderDetails = orderDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Dettaglio ordine non trovato"));
				orderDetails.setPrezzo(orderDetailsDetails.getPrezzo());
				orderDetails.setQnt(orderDetailsDetails.getQnt());
				orderDetails.setProduct(orderDetailsDetails.getProduct());
				orderDetails.setOrdine(orderDetailsDetails.getOrdine());
				orderDetails.setUser(orderDetailsDetails.getUser());
				
				return orderDetailsRepository.save(orderDetails);
	           } else {
	               throw new UnauthorizedException();
	           }
	       } else {
	       	  throw new UnauthorizedException();
	       }
	}
	
	//riservato all'admin
	@DeleteMapping("/{id}")
	public void deleteOrderDetails(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		  Token authToken = tokenService.findByToken(token);
	       if (authToken != null) {
	    	   User user = userService.getUserById(authToken.getUserId());
	           if ("ADMIN".equals(user.getRole())) { 
				OrderDetails orderDetails = orderDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Dettaglio ordine non trovato"));
				orderDetailsRepository.delete(orderDetails);
	           } else {
	               throw new UnauthorizedException();
	           }
	       } else {
	       	  throw new UnauthorizedException();
	       }
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

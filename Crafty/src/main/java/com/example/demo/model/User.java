package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
private String nome;
private String email;
private String password;
private String cognome;
private String telefono;
private String indirizzo;
private String role;
private String carrello;

@OneToMany(mappedBy="user")
private List<Ordine> ordine;

@OneToMany(mappedBy="user")
private List<OrderDetails> orderDetails;

@OneToMany(mappedBy="user")
private List<Review> review;

//getters e setters

public String getCarrello() {
	return carrello;
}
public void setCarrello(String carrello) {
	this.carrello = carrello;
}

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getCognome() {
	return cognome;
}
public void setCognome(String cognome) {
	this.cognome = cognome;
}
public String getTelefono() {
	return telefono;
}
public void setTelefono(String telefono) {
	this.telefono = telefono;
}
public String getIndirizzo() {
	return indirizzo;
}
public void setIndirizzo(String indirizzo) {
	this.indirizzo = indirizzo;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}

}

package com.example.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Token;
import com.example.demo.repository.TokenRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class TokenService {
	@Autowired
    private TokenRepository tokenRepository;

    public Token createToken(Long userId) {
        Token token = new Token();
        token.setUserId(userId);
        token.setToken(generateToken());
        token.setCreatedDate(new Date());
        return tokenRepository.save(token);
    }

    public Token findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteByToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    private String generateToken() {
    	return java.util.UUID.randomUUID().toString();
    }
}
package com.example.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.api.repository.UserRepository;
import com.example.api.security.jwt.JwtTokenProvider;
import com.example.api.vo.v1.security.AccountCredentialsVO;
import com.example.api.vo.v1.security.TokenVO;

@Service
public class AuthService {

    @Autowired
	private JwtTokenProvider tokenProvider;

    @Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsVO data) {
        if (checkIfCredentialsIsNull(data))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials data!");

        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByUsername(username);
            var tokenResponse = new TokenVO();

            if(user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username "+username+" not found!");
            }

            var token = ResponseEntity.ok(tokenResponse);
            if(token == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
            
            return token;
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }
    
    @SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {
        if (checkIfRefreshTokenIsNull(username, refreshToken))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refreshToken data!");

		var user = repository.findByUsername(username);
		var tokenResponse = new TokenVO();

		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
        
		var token = ResponseEntity.ok(tokenResponse);
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        
        return token;
	}

    private boolean checkIfRefreshTokenIsNull(String username, String refreshToken) {
        return     refreshToken == null || refreshToken.isBlank()
                || username == null || username.isBlank();
    }

    private boolean checkIfCredentialsIsNull(AccountCredentialsVO data) {
        return     data == null 
                || data.getUsername() == null || data.getUsername().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }
}

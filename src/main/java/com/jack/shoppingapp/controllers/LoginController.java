package com.jack.shoppingapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jack.shoppingapp.dto.JwtResponse;
import com.jack.shoppingapp.dto.LoginRequest;
import com.jack.shoppingapp.service.JwtService;

@RestController
@RequestMapping("/api")
public class LoginController {
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/login")
	public JwtResponse loginToShop(@Valid @RequestBody LoginRequest loginRequest) {
		return jwtService.loginToShop(loginRequest);
	}

}

package com.jack.shoppingapp.controllers;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jack.shoppingapp.dto.UserDTO;
import com.jack.shoppingapp.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService userService;

	@PostConstruct
	public void initRoleAndUser() {
		userService.initRoleAndUser();
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
		return userService.registerUser(userDTO);
	}
}

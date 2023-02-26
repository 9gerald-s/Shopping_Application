package com.jack.shoppingapp.dto;

import com.jack.shoppingapp.entity.User;

public class JwtResponse {

	private User user;
	private String jwtToken;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public JwtResponse(User user, String jwtToken) {
		super();
		this.user = user;
		this.jwtToken = jwtToken;
	}

}

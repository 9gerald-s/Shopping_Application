package com.jack.shoppingapp.dto;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = "Username is required")
	private String userName;
	@NotBlank(message = "Password is required")
	private String userPassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}

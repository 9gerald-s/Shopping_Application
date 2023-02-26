package com.jack.shoppingapp.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class UserDTO {
	@NotBlank(message = "Username is required")
	private String userName;
	@NotBlank(message = "Firstname is required")
	private String firstName;
	@NotBlank(message = "Lastname is required")
	private String lastName;
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "password is required")
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

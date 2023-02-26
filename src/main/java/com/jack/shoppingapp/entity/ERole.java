package com.jack.shoppingapp.entity;

import org.springframework.security.core.GrantedAuthority;

public enum ERole implements GrantedAuthority{
	ROLE_ADMIN,
	ROLE_USER;

	@Override
	public String getAuthority() {
		return name();
	}
	

}

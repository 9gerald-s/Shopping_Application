package com.jack.shoppingapp.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.jack.shoppingapp.models.ERole;

@Entity
public class Role {
	@Id
	@Enumerated(EnumType.STRING)
	private ERole roleName;
	private String roleDescription;
	
	public ERole getRoleName() {
		return roleName;
	}

	public void setRoleName(ERole roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

}

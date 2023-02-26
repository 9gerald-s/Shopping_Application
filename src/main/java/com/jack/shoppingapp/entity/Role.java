package com.jack.shoppingapp.entity;

import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

public class Role {
	@Id
	@Enumerated(EnumType.STRING)
	private ERole roleName;
	private String roleDescription;
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}

package com.jack.shoppingapp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jack.shoppingapp.dto.UserDTO;
import com.jack.shoppingapp.entity.ERole;
import com.jack.shoppingapp.entity.Role;
import com.jack.shoppingapp.entity.User;
import com.jack.shoppingapp.exceptions.ErrorResponse;
import com.jack.shoppingapp.repository.RoleRepository;
import com.jack.shoppingapp.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseEntity<?> registerUser(UserDTO userDTO) {
		if (userRepository.existsById(userDTO.getUserName())) {
			return ResponseEntity.badRequest()
					.body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), "Error : User name already exist"));
		}
		User user = new User();
		user.setUserName(userDTO.getUserName());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setPassword(getEncodedPassword(userDTO.getPassword()));
		user.setIsActive(true);
		user = userRepository.save(user);

		return ResponseEntity.status(200).body(user);
	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	public void initRoleAndUser() {
		Role adminRole = new Role();
		adminRole.setRoleName(ERole.ROLE_ADMIN);
		adminRole.setRoleDescription("Admin role");
		roleRepository.save(adminRole);

		Role userRole = new Role();
		userRole.setRoleName(ERole.ROLE_USER);
		userRole.setRoleDescription("Default role for newly created record");
		roleRepository.save(userRole);

		User adminUser = new User();
		adminUser.setUserName("admin123");
		adminUser.setPassword(getEncodedPassword("admin@pass"));
		adminUser.setFirstName("admin");
		adminUser.setLastName("admin");
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRoles(adminRoles);
		userRepository.save(adminUser);

	}

}

package com.jack.shoppingapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.jack.shoppingapp.dto.UserDTO;
import com.jack.shoppingapp.entity.Role;
import com.jack.shoppingapp.entity.User;
import com.jack.shoppingapp.exceptions.ErrorResponse;
import com.jack.shoppingapp.models.ChangePswdRequest;
import com.jack.shoppingapp.models.ERole;
import com.jack.shoppingapp.repository.RoleRepository;
import com.jack.shoppingapp.repository.UserRepository;
import com.jack.shoppingapp.utils.JwtUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

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
		Set<Role> userRoles = new HashSet<>();
		Role role = roleRepository.findById(ERole.ROLE_USER).get();
		userRoles.add(role);
		user.setRoles(userRoles);
		user = userRepository.save(user);

		return ResponseEntity.ok(user);
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
		adminUser.setIsActive(true);
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRoles(adminRoles);
		userRepository.save(adminUser);

	}

	public ResponseEntity<?> getAllUsers() {
		List<User> user = userRepository.findAll();
		return ResponseEntity.ok(user);
	}

	public ResponseEntity<?> deleteUser(String userName) {

		if (userRepository.existsById(userName)) {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentUserName = authentication.getName();

			if (!currentUserName.equals(userName)) {
				userRepository.deleteById(userName);
				return ResponseEntity.ok(userName + " deleted from db");
			} else {
				throw new AccessDeniedException(
						"Current User cannot current user record. please login in with different account to delete this record");
			}
		}else {
			throw new UsernameNotFoundException("User name not found in db");
		}
		
	}

	public ResponseEntity<?> changeUserPassword(ChangePswdRequest request) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userRepository.findById(userName).get();
		System.out.println(user.getUserName() + " " + userName);
		if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
			throw new BadCredentialsException("wrong credentials");
		}
		
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		
		userRepository.save(user);
		
		return ResponseEntity.ok("Password changed successfully");
	}

}

package com.jack.shoppingapp.service;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jack.shoppingapp.dto.JwtResponse;
import com.jack.shoppingapp.dto.LoginRequest;
import com.jack.shoppingapp.entity.User;
import com.jack.shoppingapp.repository.UserRepository;
import com.jack.shoppingapp.utils.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findById(username).get();
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
					getAuthorities(user));
		} else {
			throw new UsernameNotFoundException("User not valid");
		}

	}

	private Set<SimpleGrantedAuthority> getAuthorities(User user) {

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName().getAuthority())));
		return authorities;
	}

	private void authenticate(String userName, String userPassword) {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
		} catch (BadCredentialsException e) {
		}
	}

	public JwtResponse loginToShop(@Valid LoginRequest loginRequest) {
		String userName = loginRequest.getUserName();
		String userPassword = loginRequest.getUserPassword();
		authenticate(userName, userPassword);
		UserDetails userDetails = loadUserByUsername(userName);
		String token = jwtUtil.generateJwtToken(userDetails);
		User user = userRepository.findById(userName).get();
		user.setLoginAt(new DateTime());
		user.setLoginCount(user.getLoginCount() + 1);
		userRepository.save(user);
		return new JwtResponse(user, token);
	}

}

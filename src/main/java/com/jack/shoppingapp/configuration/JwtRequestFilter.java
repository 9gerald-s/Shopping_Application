package com.jack.shoppingapp.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jack.shoppingapp.service.JwtService;
import com.jack.shoppingapp.utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (!request.getRequestURI().contains("/login") && !request.getRequestURI().contains("/register")
				&& !request.getRequestURI().contains("/h2-console")) {

			final String header = request.getHeader("Auth");
			String jwtToken = null;
			String userName = null;
			if (header != null && header.startsWith("Bearer ")) {
				jwtToken = header.substring(7);

				try {
					userName = jwtUtil.getUserNameFromToken(jwtToken);
				} catch (IllegalArgumentException e) {
					System.out.println("Unable to get jwt token");
				} catch (ExpiredJwtException e) {
					System.out.println("JWT token is expired");
				}
			} else {
				System.out.println("JWT token not start with bearer or jwt is null");
			}

			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = jwtService.loadUserByUsername(userName);

				if (jwtUtil.validateJwtToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}

		}

		filterChain.doFilter(request, response);
	}

}

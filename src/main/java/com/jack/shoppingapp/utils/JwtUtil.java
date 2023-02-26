package com.jack.shoppingapp.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expirationMs}")
	private int expirationMs;

	public String generateJwtToken(UserDetails userDetails) {
		Claims claims = Jwts.claims().setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationMs));
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public boolean validateJwtToken(String token,UserDetails userDetails) {
		Claims claims = getAllClaimsFromToken(token);
		String userName = claims.getSubject();
		final Date expirationDate = claims.getExpiration();
		boolean isNotExpired = expirationDate.after(new Date());
		return (userName.equals(userDetails.getUsername()) && isNotExpired);
		
	}

	private Claims getAllClaimsFromToken(String token) {

		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
}

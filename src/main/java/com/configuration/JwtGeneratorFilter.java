package com.configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtGeneratorFilter extends OncePerRequestFilter{
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		
		String jwt=Jwts.builder()
				.setIssuer("Het Patel")
				.setSubject("jwt_token")
				.claim("username",authentication.getName())
				.claim("authorities", populateAuthorities(authentication.getAuthorities()))
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+ 48900000))
				.signWith(key).compact();
		
		
		response.setHeader(JwtConstant.JWT_HEADER, jwt);
		
		
		filterChain.doFilter(request, response);
	}
	
    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        
    	Set<String> authoritiesSet = new HashSet<>();
        
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
   
    
    }
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().equals("/signin");
//		return false;
	}

}

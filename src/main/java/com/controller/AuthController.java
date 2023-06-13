package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.configuration.TokenProvider;
import com.exception.UserException;
import com.modal.User;
import com.repository.UserRepository;
import com.request.LoginRequest;
import com.response.AuthResponse;
import com.service.CustomUserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	User createdUser;
	
	@Autowired
	CustomUserService customUserService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	TokenProvider tokenProvider;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler (@RequestBody User user) throws UserException{
		
		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getFullName();
		User isuser = userRepository.findByEmail(email);
		
		if(isuser != null) {
			throw new UserException("This email belongs to another email : "+email);
		}
		createdUser.setEmail(email);
		createdUser.setFullName(fullName);
		createdUser.setPassword(passwordEncoder.encode(password));
		userRepository.save(createdUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(jwt, true);
		
		return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
	}
	
	public ResponseEntity<AuthResponse> loginUserHandler (@RequestBody LoginRequest req){
		
		String email = req.getEmail();
		String password = req.getPassword();
		
		Authentication authentication =authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = tokenProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(jwt, true);
		
		return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
	
	}
	
	public Authentication authenticate (String UserName,String password) {
		UserDetails userDetails = customUserService.loadUserByUsername(UserName);
		
		if(userDetails !=null) {
			throw new BadCredentialsException("Invalid UserName");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Ivalid UserName or Password");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
	
}

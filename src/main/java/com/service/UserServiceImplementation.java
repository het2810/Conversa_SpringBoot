package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.configuration.TokenProvider;
import com.exception.UserException;
import com.modal.User;
import com.repository.UserRepository;
import com.request.UpdateUserRequest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class UserServiceImplementation implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TokenProvider tokenProvider;
	
	@Override
	public User findUserById(Integer userId) throws UserException {
		
		Optional<User> opt=userRepository.findById(userId);
		
		if(opt.isPresent()) {
			User user=opt.get();
			
			return user;
		}
		throw new UserException("user not exist with id "+userId);
	}

	@Override
	public User findUserProfile(String jwt) {
		String email = tokenProvider.getEmailFromToken(jwt);
		
		Optional<User> opt=userRepository.findByEmail(email);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new BadCredentialsException("recive invalid token");
	}


	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		
		System.out.println("update find user ------- ");
		User user=findUserById(userId);
		
		System.out.println("update find user ------- "+user);
		
		if(req.getFull_name()!=null) {
			user.setFull_name(req.getFull_name());
		}
		if(req.getProfile_picture()!=null) {
			user.setProfile_picture(req.getProfile_picture());
		}
		
		return userRepository.save(user);
	}

	@Override
	public List<User> searchUser(String query) {
		return userRepository.searchUser(query);
		
	}

}

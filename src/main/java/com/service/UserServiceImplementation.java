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
	public User findUserById(Integer id) throws UserException {
		Optional<User>opt=userRepository.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new UserException("User not fount with Id = "+id);
	}

	@Override
	public User findUserProfile(String jwt) throws UserException {
		String email = tokenProvider.getEmailFromToken(jwt);
		if(email == null) {
			throw new BadCredentialsException("Invalid Token Recieved..........");
		}
		
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UserException("User not found with email : "+email);
		}
		return user;
	}

	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		User user = findUserById(userId);
		
		if(req.getFullName() != null) {
			user.setFullName(req.getFullName());
		}
		if(req.getProfilePicture() != null) {
			user.setProfilePicture(req.getProfilePicture());
		}
		
		return userRepository.save(user);
	}

	@Override
	public List<User> searchUser(String query) {
		List<User> users = userRepository.searchUser(query);
		return users;
	}

}

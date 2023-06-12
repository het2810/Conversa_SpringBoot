package com.service;

import java.util.List;

import com.exception.UserException;
import com.modal.User;
import com.request.UpdateUserRequest;

public interface UserService {
	
	public User findUserById(Integer id) throws UserException;
	public User findUserProfile(String jwt) throws UserException;
	public User updateUser(Integer userId , UpdateUserRequest req) throws UserException;
	
	public List<User> searchUser(String query);
}

package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exception.UserException;
import com.modal.User;
import com.request.UpdateUserRequest;
import com.response.ApiResponse;
import com.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException{
		
		User user = userService.findUserProfile(token);
		
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("{query}")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String q){
		List<User> users = userService.searchUser(q);
		
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req,@RequestHeader("Authorization") String token) throws UserException{
		User user = userService.findUserProfile(token);
		userService.updateUser(user.getId(), req);
		ApiResponse res = new ApiResponse("User updated Successfully.....",true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
	}
	
}

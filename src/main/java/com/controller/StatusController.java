package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exception.StatusException;
import com.exception.UserException;
import com.modal.Status;
import com.modal.User;
import com.service.StatusService;
import com.service.UserService;

@RestController
@RequestMapping("/api/status")
public class StatusController {
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 private StatusService statusService;
	
	@PostMapping("/create")
	public ResponseEntity<Status> statusHandler(@RequestBody Status status ,@RequestHeader("Authorization") String token) throws UserException{
		
		User user = userService.findUserProfile(token);
		
		Status createdStatus = statusService.createStatus(status, user.getId());
		
		return new ResponseEntity<Status>(createdStatus,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<Status>> findStatusByUserId(@PathVariable Integer userId) throws UserException, StatusException{
		
		User user = userService.findUserById(userId);
		List<Status> status = statusService.findStoryByUserId(userId);
		
		return new ResponseEntity<List<Status>>(status,HttpStatus.OK);
	}
}

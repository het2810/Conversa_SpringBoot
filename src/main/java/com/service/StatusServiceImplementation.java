package com.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.UserDto;
import com.exception.StatusException;
import com.exception.UserException;
import com.modal.Status;
import com.modal.User;
import com.repository.StatusRepository;
import com.repository.UserRepository;

@Service
public class StatusServiceImplementation implements StatusService {

	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Status createStatus(Status status, Integer userId) throws UserException {
		
		User user = userService.findUserById(userId);
		UserDto userDto = new UserDto();
		
		userDto.setEmail(user.getEmail());
		userDto.setFull_name(user.getFull_name());
		userDto.setId(user.getId());
		userDto.setProfile_picture(user.getProfile_picture());
		
		status.setUser(userDto);
		status.setTimeStamp(LocalDateTime.now());
		
		user.getStatus().add(status);
		
		
		return statusRepository.save(status);
	}

	@Override
	public List<Status> findStoryByUserId(Integer userId) throws UserException, StatusException {
		
		User user = userService.findUserById(userId);
		
		List<Status> status = user.getStatus();
		
		if(status.size()==0) {
			throw new StatusException("No status Available......");
		}
		
		return status;
	}

}

package com.service;

import java.util.List;

import com.exception.StatusException;
import com.exception.UserException;
import com.modal.Status;

public interface StatusService {
	public Status createStatus(Status status ,Integer userId) throws UserException;
	
	public List<Status> findStoryByUserId(Integer userId) throws UserException,StatusException;
}

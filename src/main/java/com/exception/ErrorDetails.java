package com.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class ErrorDetails {
	
	private String error; 
	private String message; 
	private LocalDateTime timeStamp;
	

	
}

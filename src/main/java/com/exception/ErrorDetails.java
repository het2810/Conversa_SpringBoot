package com.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public class ErrorDetails {
	
	private String error; 
	private String message; 
	private LocalDateTime timeStamp;
	

	
}

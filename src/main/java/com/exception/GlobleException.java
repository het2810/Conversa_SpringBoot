package com.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobleException {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> userExceptionHandler(UserException e, WebRequest req) {

		ErrorDetails error = new ErrorDetails(e.getMessage(), req.getDescription(false), LocalDateTime.now());

		return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MessageException.class)
	public ResponseEntity<ErrorDetails> messageExceptionHandler(MessageException e, WebRequest req) {

		ErrorDetails error = new ErrorDetails(e.getMessage(), req.getDescription(false), LocalDateTime.now());

		return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, WebRequest req) {

		String err = e.getBindingResult().getFieldError().getDefaultMessage();
		
		ErrorDetails error = new ErrorDetails("Validation Error .....", err, LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> noHandlerFoundExceptionHandler(NoHandlerFoundException e, WebRequest req) {

		ErrorDetails error = new ErrorDetails("End point not found.....",e.getMessage(), LocalDateTime.now());

		return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception e, WebRequest req) {

		ErrorDetails error = new ErrorDetails(e.getMessage(), req.getDescription(false), LocalDateTime.now());

		return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
	}
}

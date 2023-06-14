package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exception.ChatException;
import com.exception.MessageException;
import com.exception.UserException;
import com.modal.Message;
import com.modal.User;
import com.request.SendMessageRequest;
import com.response.ApiResponse;
import com.service.MessageService;
import com.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
@NoArgsConstructor
public class MessageController {
		
	@Autowired
	MessageService messageService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Message> sendMessage(@RequestBody SendMessageRequest req ,@RequestHeader("Authorization")String jwt ) throws UserException, ChatException{
		User user = userService.findUserProfile(jwt);
		req.setUserId(user.getId());
		Message msg = messageService.sendMessage(req);
		
		return new ResponseEntity<Message>(msg,HttpStatus.OK);
	}

	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getChatsMessageHandler(@PathVariable Integer chatId,@RequestHeader("Authorization")String jwt ) throws UserException, ChatException{
		User user = userService.findUserProfile(jwt);
		List<Message> msg = messageService.getChatsMessages(chatId, user);
		
		return new ResponseEntity<List<Message>>(msg,HttpStatus.OK);
	}
	
	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId,@RequestHeader("Authorization")String jwt ) throws UserException, MessageException{
		User user = userService.findUserProfile(jwt);
		messageService.deleteMessage(messageId, user);
		ApiResponse res = new ApiResponse("Message Deleted Successfully",true); 
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK); 
	
	}
	
	
}

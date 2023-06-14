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
import com.exception.UserException;
import com.modal.Chat;
import com.modal.User;
import com.request.GroupChatRequest;
import com.request.SingleChatRequest;
import com.response.ApiResponse;
import com.service.ChatService;
import com.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/chats")
public class ChatController {

	@Autowired
	ChatService chatService;
	
	@Autowired
	UserService userService;

	@PostMapping("/single")
	public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,@RequestHeader("Authorization")String jwt ) throws UserException{
		
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());

		return new ResponseEntity<Chat>(chat,HttpStatus.OK); 
		
	}

	@PostMapping("/group")
	public ResponseEntity<Chat> createGroupChatHandler(@RequestBody GroupChatRequest req,@RequestHeader("Authorization")String jwt ) throws UserException{
		
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createGroup(req, reqUser);
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK); 
		
	}

	@GetMapping("/{chatId}")
	public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId ,@RequestHeader("Authorization")String jwt ) throws UserException, ChatException{
		
		Chat chat = chatService.findChatById(chatId);
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);	
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findAllChatByUserIdHandler(@RequestHeader("Authorization")String jwt ) throws UserException{
		
		User reqUser = userService.findUserProfile(jwt);
		List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
		
		return new ResponseEntity<List<Chat>>(chats,HttpStatus.OK); 
		
	}
	
	@GetMapping("/{chatId}/add/userId")
	public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId,@PathVariable Integer userId,@RequestHeader("Authorization")String jwt ) throws UserException, ChatException{
		
		User reqUser = userService.findUserProfile(jwt);
		Chat chats = chatService.addUserToGroup(userId, chatId, reqUser);
		
		return new ResponseEntity<Chat>(chats,HttpStatus.OK); 
		
	}
	
	@GetMapping("/{chatId}/remove/userId")
	public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable Integer chatId,@PathVariable Integer userId,@RequestHeader("Authorization")String jwt ) throws UserException, ChatException{
		
		User reqUser = userService.findUserProfile(jwt);
		Chat chats = chatService.removeFromGroup(chatId, userId, reqUser);
		
		return new ResponseEntity<Chat>(chats,HttpStatus.OK); 
		
	}
	
	@DeleteMapping("/delete/{chatId}")
	public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId,@RequestHeader("Authorization")String jwt ) throws UserException, ChatException{
		
		User reqUser = userService.findUserProfile(jwt);
		chatService.deleteChat(chatId, reqUser.getId());
		
		ApiResponse res = new ApiResponse("Chat Deleted Successfully",true); 
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK); 
		
	}
	
	
}

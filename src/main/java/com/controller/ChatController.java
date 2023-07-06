package com.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controller.mapper.ChatDtoMapper;
import com.dto.ChatDto;
import com.exception.ChatException;
import com.exception.UserException;
import com.modal.Chat;
import com.modal.User;
import com.request.GroupChatRequest;
import com.request.RenameGroupRequest;
import com.request.SingleChatRequest;
import com.service.ChatService;
import com.service.UserService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

//	private ChatRepository chatRepo;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/single")
	public ResponseEntity<ChatDto> creatChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization")  String jwt) throws UserException{
		
		System.out.println("single chat --------");
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.createChat(reqUser.getId(),singleChatRequest.getUserId(),false);
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@PostMapping("/group")
	public ResponseEntity<ChatDto> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String jwt) throws UserException{
		
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.createGroup(groupChatRequest, reqUser.getId());
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
		
	}
	
	@GetMapping("/{chatId}")
	public ResponseEntity<ChatDto> findChatByIdHandler(@PathVariable Integer chatId) throws ChatException{
		
		Chat chat =chatService.findChatById(chatId);
		
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<ChatDto>> findAllChatByUserIdHandler(@RequestHeader("Authorization")String jwt) throws UserException{
		
		User user=userService.findUserProfile(jwt);
		
		List<Chat> chats=chatService.findAllChatByUserId(user.getId());
		
		List<ChatDto> chatDtos=ChatDtoMapper.toChatDtos(chats);
		
		return new ResponseEntity<List<ChatDto>>(chatDtos,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<ChatDto> addUserToGroupHandler(@PathVariable Integer chatId,@PathVariable Integer userId) throws UserException, ChatException{
		
		Chat chat=chatService.addUserToGroup(userId, chatId);
		
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/rename")
	public ResponseEntity<ChatDto> renameGroupHandler(@PathVariable Integer chatId,@RequestBody RenameGroupRequest renameGoupRequest, @RequestHeader("Autorization") String jwt) throws ChatException, UserException{
		
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat =chatService.renameGroup(chatId, renameGoupRequest.getGroupName(), reqUser.getId());
		
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<ChatDto> removeFromGroupHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer chatId,@PathVariable Integer userId) throws UserException, ChatException{
		
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.removeFromGroup(chatId, userId, reqUser.getId());
		
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{chatId}/{userId}")
	public ResponseEntity<ChatDto> deleteChatHandler(@PathVariable Integer chatId, @PathVariable Integer userId) throws ChatException, UserException{
		
		Chat chat=chatService.deleteChat(chatId, userId);
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
}

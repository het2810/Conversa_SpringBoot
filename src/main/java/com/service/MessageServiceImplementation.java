package com.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exception.ChatException;
import com.exception.MessageException;
import com.exception.UserException;
import com.modal.Chat;
import com.modal.Message;
import com.modal.User;
import com.repository.ChatRepository;
import com.repository.MessageRepository;
import com.request.SendMessageRequest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MessageServiceImplementation implements MessageService{

	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ChatService chatService;
	
	
	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
		User user = userService.findUserById(req.getUserId());
		Chat chat = chatService.findChatById(req.getChatId());
		
		Message msg = new Message();
		msg.setUser(user);
		msg.setChat(chat);
		msg.setContent(req.getContent());
		msg.setTimeStamp(LocalDateTime.now());
		
		return msg;
	}

	@Override
	public List<Message> getChatsMessages(Integer chatId , User reqUser) throws ChatException, UserException {
		
		Chat chat = chatService.findChatById(chatId);
		 if(!chat.getUsers().contains(reqUser)) {
			 throw new UserException("You are not reeted to this chat "+chat.getId());
		 }
		
		List<Message> msg = messageRepository.findChatById(chat.getId());
		
		return msg;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		Optional<Message> opt = messageRepository.findById(messageId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new MessageException("Message Not Found with MessageId : "+messageId);
	}

	@Override
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException, UserException {
		Message msg = findMessageById(messageId);
		if(msg.getUser().getId().equals(reqUser.getId())) {
			messageRepository.deleteById(messageId);
		}
		throw new UserException("You cannot delete other users message "+reqUser.getFullName());
	}

}

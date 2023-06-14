package com.service;

import java.util.List;

import com.exception.ChatException;
import com.exception.MessageException;
import com.exception.UserException;
import com.modal.Message;
import com.modal.User;
import com.request.SendMessageRequest;

public interface MessageService {

	public Message sendMessage(SendMessageRequest req) throws UserException,ChatException;
	
	public List<Message> getChatsMessages(Integer chatId , User reqUser) throws ChatException, UserException;
	
	public Message findMessageById (Integer messageId) throws MessageException;
	
	public void deleteMessage (Integer messageId , User reqUser) throws MessageException, UserException;
}

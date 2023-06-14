package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exception.ChatException;
import com.exception.UserException;
import com.modal.Chat;
import com.modal.User;
import com.repository.ChatRepository;
import com.request.GroupChatRequest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ChatServiceImplementation implements ChatService {

	@Autowired
	ChatRepository chatRepository;

	@Autowired
	UserService userService;

	@Override
	public Chat createChat(User reqUser, Integer userId) throws UserException {
		User user = userService.findUserById(userId);

		Chat isChatExist = chatRepository.findSingleChatByUserIds(user, reqUser);

		if (isChatExist == null) {
			return isChatExist;
		}
		Chat chat = new Chat();
		chat.setCreatedBy(user);
		chat.getUsers().add(user);
		chat.getUsers().add(reqUser);
		chat.setIsGroup(false);

		return chat;
	}

	@Override
	public Chat findChatById(Integer chatId) throws ChatException {
		Optional<Chat> chat = chatRepository.findById(chatId);

		if (chat.isPresent()) {
			return chat.get();
		}
		throw new ChatException("chat not found with ChatId : " + chatId);
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {

		User user = userService.findUserById(userId);

		List<Chat> chats = chatRepository.findChatByUserId(user.getId());

		return chats;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {

		Chat group = new Chat();
		group.setIsGroup(true);
		group.setChatImage(req.getChatImage());
		group.setChatName(req.getChatName());
		group.setCreatedBy(reqUser);
		group.getAdmins().add(reqUser);

		for (Integer userId : req.getUserIds()) {
			User user = userService.findUserById(userId);
			group.getUsers().add(user);
		}

		return group;
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws ChatException, UserException {

		Optional<Chat> opt = chatRepository.findById(chatId);

		User user = userService.findUserById(userId);

		if (opt.isPresent()) {
			Chat chat = opt.get();

			if (chat.getAdmins().contains(reqUser)) {
				chat.getUsers().add(user);
				return chatRepository.save(chat);
			} else {
				throw new UserException("Admins can add users only !!");
			}
		}

		throw new ChatException("Chat Not Found with id :" + chatId);
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		if (opt.isPresent()) {
			Chat chat = opt.get();
			if (chat.getUsers().contains(reqUser)) {
				chat.setChatName(groupName);
				return chatRepository.save(chat);
			}
			throw new UserException("You are not a member of this group");
		}

		throw new ChatException("Chat Not Found with id :" + chatId);
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws ChatException, UserException {
		Optional<Chat> opt = chatRepository.findById(chatId);

		User user = userService.findUserById(userId);

		if (opt.isPresent()) {
			Chat chat = opt.get();

			if (chat.getAdmins().contains(reqUser)) {
				chat.getUsers().remove(user);
				return chatRepository.save(chat);
			}
			else if (chat.getUsers().contains(reqUser)) {
				if(user.getId().equals(reqUser.getId())) {
					chat.getUsers().remove(user);
					return chatRepository.save(chat);
				
				}
				
			}
			throw new UserException("Admins can add users only !!");	
		}

		throw new ChatException("Chat Not Found with id :" + chatId);

	}

	@Override
	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		if(opt.isPresent()) {
			Chat chat = opt.get();
			chatRepository.deleteById(chat.getId());
		}
	}

}

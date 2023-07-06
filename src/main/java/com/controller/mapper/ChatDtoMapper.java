package com.controller.mapper;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dto.ChatDto;
import com .dto.MessageDto;
import com .dto.UserDto;
import com.modal.Chat;

public class ChatDtoMapper {
	
	public static ChatDto toChatDto(Chat chat) {
		
		UserDto userDto=UserDtoMapper.toUserDTO(chat.getCreated_by());
		List<MessageDto> messageDtos=MessageDtoMapper.toMessageDtos(chat.getMessages());
		Set<UserDto> userDtos=UserDtoMapper.toUserDtos(chat.getUsers());
		Set<UserDto> admins=UserDtoMapper.toUserDtos(chat.getAdmins());
		
		ChatDto chatDto=new ChatDto();
		chatDto.setId(chat.getId());
		chatDto.setChat_image(chat.getChat_image());
		chatDto.setChat_name(chat.getChat_name());
		chatDto.setCreated_by(userDto);
		chatDto.setIs_group(chat.getIs_group());
		chatDto.setMessages(messageDtos);
		chatDto.setUsers(userDtos);
		chatDto.setAdmins(admins);
		
		
		return chatDto;
	}
	
	public static List<ChatDto> toChatDtos(List<Chat> chats){
		
		List<ChatDto> chatDtos=new ArrayList<>();
		
		for(Chat chat:chats) {
			ChatDto chatDto=toChatDto(chat);
			chatDtos.add(chatDto);
		}
		
		return chatDtos;
	}

}

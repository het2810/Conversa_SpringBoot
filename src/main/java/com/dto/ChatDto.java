package com.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

	private Integer id;
	private String chat_name;
	private String chat_image;
	
	@Column(columnDefinition = "boolean default false")
	private Boolean is_group;
	
	private Set<UserDto> admins= new HashSet<>();
	
	private UserDto created_by;

	private Set<UserDto> users = new HashSet<>();
	
	private List<MessageDto> messages=new ArrayList<>();

	
	
	
}

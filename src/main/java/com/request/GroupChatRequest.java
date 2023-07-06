package com.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatRequest {
	private List<Integer> userIds;
	private String chat_name;
	private String chat_image;
}

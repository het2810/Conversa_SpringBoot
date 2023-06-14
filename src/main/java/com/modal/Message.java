package com.modal;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String content;
	
	private LocalDateTime timeStamp;
	
	@ManyToOne  //one user can create multiple messages 
	private User user;
	
	@ManyToOne  // many chat can be related to 1 user only 
	@JoinColumn(name = "chat_id")
	private Chat chat;
	
}

package com.modal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String chatName;
	private String chatImage;
	

	@ManyToMany	//Multiple admins canbe created in a single group
	private Set<User> admins = new HashSet<>(); //we've taken set because users must be unique
	
	
	@Column(name = "is_group")
	private Boolean isGroup;
	
	@JoinColumn(name = "created_by")
	@ManyToOne //1 user can create multiple chat but chat cannot be created by multiple users
	private User createdBy;
	
	@ManyToMany	//Multiple users can create multiple chats
	private Set<User> users = new HashSet<>(); //we've taken set because users must be unique
	
	@OneToMany //many messages for 1 chat
	private List<Message> messages = new ArrayList<>();
	
}

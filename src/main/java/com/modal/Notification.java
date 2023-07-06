package com.modal;

import java.time.LocalDateTime;



import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String message;
	private Boolean is_seen;
	private LocalDateTime timestamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	
}

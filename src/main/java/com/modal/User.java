package com.modal;


import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String fullName;
	private String profilePicture;
	private String email;
	private String password;
	@Override
	public int hashCode() {
		return Objects.hash(email, fullName, id, password, profilePicture);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(profilePicture, other.profilePicture);
	}
	
}

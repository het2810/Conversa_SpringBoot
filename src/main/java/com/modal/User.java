package com.modal;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	private String full_name;
	private String email;
	private String profile_picture;
	private String password;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Notification> notifications=new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Status> status = new ArrayList<>();
	
	@Override
	public String toString() {
		return "User [id=" + id + ", full_name=" + full_name + ", email=" + email + ", notifications=" + notifications
				+ "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, full_name, id, password, profile_picture);
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
		return Objects.equals(email, other.email) && Objects.equals(full_name, other.full_name)
				&& Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(profile_picture, other.profile_picture);
	}
	
}

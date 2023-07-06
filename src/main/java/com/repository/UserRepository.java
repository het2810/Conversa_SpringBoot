package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.modal.User;

public interface UserRepository extends  JpaRepository<User, Integer>{

	public Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.full_name LIKE %:name%")
	List<User> searchUser(@Param("name") String name);
}

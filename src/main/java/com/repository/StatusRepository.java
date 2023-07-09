package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.modal.Status;

public interface StatusRepository extends JpaRepository<Status, Integer>{
	
	@Query("select s from Status s where s.user.id = :userId")
	public List<Status> findStatusByUserId(@Param("userId") Integer userId);
	
}

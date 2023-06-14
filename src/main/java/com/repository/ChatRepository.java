package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.modal.Chat;
import com.modal.User;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

	@Query("select c from Chat c join c.users u where u.id =: userId")
	public List<Chat> findChatByUserId(@Param("userId") Integer userId);
	
	@Query("SELECT c FROM Chat c JOIN c.users u1 JOIN c.users u2 WHERE c.isGroup = false AND u1 = :user AND u2 = :reqUser")
	public Chat findSingleChatByUserIds(@Param("user")User user,@Param("reqUser")User reqUser);
}

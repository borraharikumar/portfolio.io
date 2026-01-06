package com.hari_world.portfolio.reposirory;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hari_world.portfolio.entity.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Integer> {
	
	Integer countByReadStatusFalse();
	List<ContactMessage> findByReadStatusFalse();
	List<ContactMessage> findByReadStatusFalse(Pageable pageable);
	
	@Modifying
	@Query("UPDATE ContactMessage SET readStatus = TRUE WHERE id = :id")
	void markMessageAsRead(Integer id);

}

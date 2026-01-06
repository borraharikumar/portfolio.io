package com.hari_world.portfolio.reposirory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hari_world.portfolio.entity.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

	boolean existsByEmail(String email);

	@Query("SELECT s.email FROM Subscriber s WHERE active = TRUE")
	List<String> findEmailsByActiveTrue();

	Optional<Subscriber> findByEmail(String email);

	@Query("SELECT COUNT(*) FROM Subscriber s WHERE s.email = :email")
	Integer getCountByEmail(String email);

	@Query("SELECT COUNT(*) FROM Subscriber s WHERE s.active = TRUE")
	Integer findCountOfActiveSubscribers();

	@Query("SELECT COUNT(*) FROM Subscriber s WHERE s.active = FALSE")
	Integer findCountOfInactiveSubscribers();

}

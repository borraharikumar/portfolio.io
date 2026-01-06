package com.hari_world.portfolio.reposirory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hari_world.portfolio.entity.LoginCredentials;

public interface LoginCredentialsRepository extends JpaRepository<LoginCredentials, Integer> {

	Optional<LoginCredentials> findByUsername(String username);

	@Modifying
	@Query("UPDATE LoginCredentials lc SET lc.password = :password WHERE lc.username = :username")
	Integer changePassword(String username, String password);

	Integer countByRole(String role);

	List<LoginCredentials> findByRole(String role);

}
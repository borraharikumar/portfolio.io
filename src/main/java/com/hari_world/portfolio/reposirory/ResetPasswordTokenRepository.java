package com.hari_world.portfolio.reposirory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hari_world.portfolio.entity.ResetPasswordToken;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Integer> {

	Optional<ResetPasswordToken> findByToken(String token);

}

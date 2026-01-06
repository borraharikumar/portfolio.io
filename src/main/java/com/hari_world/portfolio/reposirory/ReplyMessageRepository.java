package com.hari_world.portfolio.reposirory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hari_world.portfolio.entity.ReplyMessage;

public interface ReplyMessageRepository extends JpaRepository<ReplyMessage, Long> {
}

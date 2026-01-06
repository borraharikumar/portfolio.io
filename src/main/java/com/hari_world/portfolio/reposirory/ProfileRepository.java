package com.hari_world.portfolio.reposirory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hari_world.portfolio.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}

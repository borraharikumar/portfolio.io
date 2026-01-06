package com.hari_world.portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hari_world.portfolio.entity.Profile;
import com.hari_world.portfolio.exception.UserNotFoundException;
import com.hari_world.portfolio.reposirory.ProfileRepository;

@Service
public class ProfileServiceImpl implements IProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public Profile getProfileData() {
		return profileRepository.findById(1).get();
	}

	@Override
	public Profile getProfileData(Integer id) {
		return profileRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id '" + id + "'"));
	}

	@Override
	public Profile saveProfile(Profile profile) {
		return profileRepository.save(profile);
	}

}
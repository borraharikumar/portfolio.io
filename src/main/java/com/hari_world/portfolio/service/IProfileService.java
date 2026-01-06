package com.hari_world.portfolio.service;

import com.hari_world.portfolio.entity.Profile;

public interface IProfileService {
	
	public Profile getProfileData();
	public Profile getProfileData(Integer id);
	public Profile saveProfile(Profile profile);

}
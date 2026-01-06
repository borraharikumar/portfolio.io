package com.hari_world.portfolio.service;

import java.util.List;

import com.hari_world.portfolio.entity.Banner;

public interface IBannerService {
	
	public Banner saveBanner(Banner banner);
	public List<Banner> getLatestFiveBanners();
	public List<Banner> getAllbanners();
	public Banner getBanner(Integer id);
	public void deleteBanner(Integer id);

}

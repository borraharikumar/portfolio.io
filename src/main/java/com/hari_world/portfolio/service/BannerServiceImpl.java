package com.hari_world.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hari_world.portfolio.entity.Banner;
import com.hari_world.portfolio.exception.BannerNotFoundException;
import com.hari_world.portfolio.reposirory.BannerRepository;

@Service
public class BannerServiceImpl implements IBannerService {

	@Autowired
	private BannerRepository bannerRepository;

	@Override
	public Banner saveBanner(Banner banner) {
		return bannerRepository.save(banner);
	}

	@Override
	public List<Banner> getLatestFiveBanners() {
		return bannerRepository.findLatestFiveBanners(PageRequest.of(0, 5));
	}

	@Override
	public List<Banner> getAllbanners() {
		return bannerRepository.findAll();
	}

	@Override
	public Banner getBanner(Integer id) {
		return bannerRepository.findById(id)
				.orElseThrow(() -> new BannerNotFoundException("Banner not found with id '" + id + "'"));
	}

	@Override
	public void deleteBanner(Integer id) {
		bannerRepository.deleteById(id);
	}

}
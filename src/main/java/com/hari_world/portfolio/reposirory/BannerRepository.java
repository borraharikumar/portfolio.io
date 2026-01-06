package com.hari_world.portfolio.reposirory;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hari_world.portfolio.entity.Banner;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

	@Query("SELECT b FROM Banner b ORDER BY b.createdAt DESC")
	List<Banner> findLatestFiveBanners(Pageable pageable);

}
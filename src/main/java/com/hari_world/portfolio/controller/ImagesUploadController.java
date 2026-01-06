package com.hari_world.portfolio.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@RestController
@RequestMapping("/admin/images")
public class ImagesUploadController {

	@Autowired
	private Cloudinary cloudinary;

	@PostMapping("/upload")
	public Map<String, Object> uploadImage(@RequestParam MultipartFile file) throws IOException {
		Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
		String imageUrl = uploadResult.get("secure_url").toString();
		return Map.of("location", imageUrl);
	}

}

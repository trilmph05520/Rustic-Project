package com.vn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vn.jpa.Blog;

public interface BlogService {

	Page<Blog> findAllByTitle(String title,Pageable pageable);
	
	Blog insert(Blog blog);
	
}

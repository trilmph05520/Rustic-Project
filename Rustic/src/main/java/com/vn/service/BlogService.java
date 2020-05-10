package com.vn.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vn.jpa.Blog;

public interface BlogService {

	Page<Blog> findAllByTitle(String title, Pageable pageable);

	Blog insert(Blog blog);

	Blog update(Blog blog);

	Blog findOne(Long id);

	void delete(Blog blog);
	
	List<Blog> lsBlogDateDesc();

}

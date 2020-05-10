package com.vn.service.imple;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vn.jpa.Blog;
import com.vn.repository.BlogRepo;
import com.vn.service.BlogService;

@Service(value = "blogService")
@Transactional
public class BlogServiceImpl implements BlogService{

	@Resource
	private BlogRepo blogRepo;
	
	@Override
	public Page<Blog> findAllByTitle(String title, Pageable pageable) {
		return blogRepo.findAllByTitle(title, pageable);
	}

	@Override
	public Blog insert(Blog blog) {
		return blogRepo.save(blog);
	}

}

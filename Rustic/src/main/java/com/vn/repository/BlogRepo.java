package com.vn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vn.jpa.Blog;

@Repository(value = "blogRepo")
public interface BlogRepo extends JpaRepository<Blog, Long>{

	@Query(value = "SELECT b FROM Blog b WHERE "
			+ "(:title IS NULL OR :title = '' OR b.title LIKE CONCAT('%', :title, '%'))")
	Page<Blog> findAllByTitle(@Param("title") String title,Pageable pageable);
	
}

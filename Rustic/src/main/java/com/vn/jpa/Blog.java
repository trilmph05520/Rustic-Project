package com.vn.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "blog")
@NamedQuery(name = "Blog.findAll", query = "SELECT b FROM Blog b")
public class Blog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;
	
	@Column(name = "mainImg", columnDefinition = "TEXT")
	private String mainImg;

	@Column(name = "subImg", columnDefinition = "TEXT")
	private String subImg;
	
	@Column(name = "mainDescription")
	private String mainDescription;
	
	@Column(name = "subDescription")
	private String subDescription;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "created")
	private Date created;

	
	
	public Blog() {
		super();
	}

	public Blog(Long id, String title, String mainImg, String subImg, String mainDescription, String subDescription,
			Date created) {
		super();
		this.id = id;
		this.title = title;
		this.mainImg = mainImg;
		this.subImg = subImg;
		this.mainDescription = mainDescription;
		this.subDescription = subDescription;
		this.created = created;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMainImg() {
		return mainImg;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

	public String getSubImg() {
		return subImg;
	}

	public void setSubImg(String subImg) {
		this.subImg = subImg;
	}

	public String getMainDescription() {
		return mainDescription;
	}

	public void setMainDescription(String mainDescription) {
		this.mainDescription = mainDescription;
	}

	public String getSubDescription() {
		return subDescription;
	}

	public void setSubDescription(String subDescription) {
		this.subDescription = subDescription;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	

}

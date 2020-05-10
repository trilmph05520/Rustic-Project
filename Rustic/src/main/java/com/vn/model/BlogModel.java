package com.vn.model;

import java.util.Date;

import com.vn.common.FileUtils.DescriptionBase64File;

public class BlogModel {
	private Long id;
	private String title;
	private DescriptionBase64File mainImg;
    private DescriptionBase64File[] subImg;
    private String mainDescription;
    private String subDescription;
    private Date created;
    private String removeSub;
    
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
	public DescriptionBase64File getMainImg() {
		return mainImg;
	}
	public void setMainImg(DescriptionBase64File mainImg) {
		this.mainImg = mainImg;
	}
	public DescriptionBase64File[] getSubImg() {
		return subImg;
	}
	public void setSubImg(DescriptionBase64File[] subImg) {
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
	public BlogModel() {
		super();
	}
	public String getRemoveSub() {
		return removeSub;
	}
	public void setRemoveSub(String removeSub) {
		this.removeSub = removeSub;
	}
    
    
}

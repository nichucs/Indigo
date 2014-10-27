package com.thoughtripples.indigokernal.model;

public class ContactModel {

	String image_url,name,desc,phno;

	public ContactModel(String image_url, String name, String desc, String phno) {
		super();
		this.image_url = image_url;
		this.name = name;
		this.desc = desc;
		this.phno = phno;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}
	
}

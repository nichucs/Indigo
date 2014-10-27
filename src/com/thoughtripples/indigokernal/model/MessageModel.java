package com.thoughtripples.indigokernal.model;

public class MessageModel {

	int id;
	String head,desc,date,image_url;
	public MessageModel(int id, String head, String desc, String date,
			String image_url) {
		super();
		this.id = id;
		this.head = head;
		this.desc = desc;
		this.date = date;
		this.image_url = image_url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
}

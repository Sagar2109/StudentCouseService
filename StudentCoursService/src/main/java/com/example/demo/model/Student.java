package com.example.demo.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "StudentInfo")
public class Student {
	@Id
	private String id;
	private String name;
	private String email;
	private String contactNo;
	private List<String> courseIds;
	private Boolean suspended;
	private Date modifiedAt = new Date();
	private Date createdAt = new Date();
	
	
	public Student() {
		super();
	
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public List<String> getCourseIds() {
		return courseIds;
	}
	public void setCourseIds(List<String> courseIds) {
		this.courseIds = courseIds;
	}
	public Boolean getSuspended() {
		return suspended;
	}
	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}
	public Date getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	

}

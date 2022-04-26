package com.example.demo.dto;

import java.util.List;

import com.example.demo.medel.Course;


public class StudentDTO {
	
	private String id;
	private String name;
	private String email;
	private String contactNo;
	private List<Course> courses;
	
	
	public StudentDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> list) {
		this.courses = list;
	}

}

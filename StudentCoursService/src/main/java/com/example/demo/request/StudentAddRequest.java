package com.example.demo.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class StudentAddRequest {

	private String id;
	@NotBlank
	private String name;
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email Not In Format")
	@Pattern(regexp = "^(?!\\s*$).+", message = "Email Not Valid")
	private String email;
	private String contactNo;
	private List<String> courseIds;

	public StudentAddRequest() {
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

}

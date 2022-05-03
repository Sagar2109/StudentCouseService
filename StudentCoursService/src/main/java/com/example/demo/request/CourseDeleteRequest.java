package com.example.demo.request;

import javax.validation.constraints.NotBlank;

public class CourseDeleteRequest {

	@NotBlank
	private String cid;
	private Boolean suspended;
	
	

	public CourseDeleteRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Boolean getSuspended() {
		return suspended;
	}

	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}

}

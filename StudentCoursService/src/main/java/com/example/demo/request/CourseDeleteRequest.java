package com.example.demo.request;

import javax.validation.constraints.NotNull;

public class CourseDeleteRequest {

	@NotNull
	private String cid;
	private Boolean suspended;

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

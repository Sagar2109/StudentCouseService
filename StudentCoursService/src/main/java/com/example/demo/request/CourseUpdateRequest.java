package com.example.demo.request;

import javax.validation.constraints.NotBlank;

public class CourseUpdateRequest {

	@NotBlank
	private String cid;
	@NotBlank
	private String cname;
	@NotBlank
	private String cdesc;
	@NotBlank
	private String createdBy;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCdesc() {
		return cdesc;
	}
	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}

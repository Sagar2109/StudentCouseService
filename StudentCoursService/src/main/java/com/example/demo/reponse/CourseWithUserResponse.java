package com.example.demo.reponse;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CourseWithUserResponse {

	private String cid;
	private String cname;
	private String cdesc;
	private Boolean suspended;
	private Date modifiedAt;
	private Date createdAt;
	private UserResponse createdByUser;
	private String createdBy;


	public CourseWithUserResponse() {
		super();
	}

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

	public UserResponse getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(UserResponse createdByUser) {
		this.createdByUser = createdByUser;
	}
	@JsonIgnore
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}

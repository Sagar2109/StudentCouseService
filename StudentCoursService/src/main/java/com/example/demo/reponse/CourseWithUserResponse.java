package com.example.demo.reponse;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "CourseInfo")
public class CourseWithUserResponse {

	@Id
	private String cid;
	private String cname;
	private String cdesc;
	private Boolean suspended;
	private Date modifiedAt=new Date();
	private Date createdAt=new Date();
	private UserResponse createdByUser;

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

	public void setSupended(Boolean suspended) {
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

	public boolean isSuspended() {
		return suspended;
	}

}

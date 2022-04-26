package com.example.demo.request;

import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class ListPageRequest {
     @Min(1)
	private long page;
	private long totalInList;
	private String searchText;
	private String[] fields;

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getTotalInList() {
		return totalInList;
	}

	public void setTotalInList(long totalInList) {
		this.totalInList = totalInList;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

}

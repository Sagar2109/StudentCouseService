package com.example.demo.reponse;

import java.util.List;

import com.example.demo.model.Student;

public class CourseStudentDetailResponse {

	private String id;
	private String name;
	private String desc;
	private List<Student> students;

	public String get_id() {
		return id;
	}

	public void set_id(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}

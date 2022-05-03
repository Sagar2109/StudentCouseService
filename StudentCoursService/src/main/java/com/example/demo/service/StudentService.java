package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.StudentDTO;
import com.example.demo.model.Student;

public interface StudentService {

	public List<Student> findAll();

	public StudentDTO findById(String id);

	public Student findByEmail(String email);

	

	public String delete(String id);

	public Object insert(Student student);

	public Student update(Student student);

	public List<Student> findAllByCourses(List<String> courseid);

}

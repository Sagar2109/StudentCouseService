package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.StudentDTO;
import com.example.demo.model.Student;
import com.example.demo.request.StudentAddRequest;
import com.example.demo.request.StudentUpdateRequest;

public interface StudentService {

	public List<Student> findAll();

	public StudentDTO findById(String id);

	public Student findByEmail(String email);

	public Student delete(String id);

	public Object insert(StudentAddRequest request);

	public Student update(StudentUpdateRequest request);

	public List<Student> findAllByCourses(List<String> courseid);

}

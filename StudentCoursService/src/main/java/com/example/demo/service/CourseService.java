package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CourseDTO;
import com.example.demo.medel.Course;
import com.example.demo.reponse.CourseStudentDetailResponse;
import com.example.demo.request.ListPageRequest;

public interface CourseService {

	public List<Course> findAll();

	public Course find(String id);

	public String delete(String id);

	public Object insert(Course course);

	public Course update(Course course);

	public List<Course> findAllCourses(List<String> list);

	public CourseStudentDetailResponse findCourseStudentDetails(String id);

	boolean isCourseNameExists(String cname);

	public List<Course> findPage(ListPageRequest request);

	public List<CourseDTO> findAllCoursesByLookup();

}

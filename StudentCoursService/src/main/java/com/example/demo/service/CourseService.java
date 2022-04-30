package com.example.demo.service;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.dto.CourseDTO;
import com.example.demo.medel.Course;
import com.example.demo.reponse.CourseStudentDetailResponse;
import com.example.demo.reponse.CourseWithUserResponse;
import com.example.demo.request.CourseDeleteRequest;
import com.example.demo.request.CourseUpdateRequest;
import com.example.demo.request.ListPageRequest;

public interface CourseService {

	public List<Course> findAll();

	public Object insert(Course course);

	

	public List<Course> findAllCourses(List<String> list);

	public CourseStudentDetailResponse findCourseStudentDetails(String id);

	boolean isCourseNameExists(String cname);

	public List<Course> findPage(ListPageRequest request);

	public List<CourseDTO> findAllCoursesByLookup();

	public CourseWithUserResponse findCourseById(String id);

	public Course deleteCourse(CourseDeleteRequest courseDeleteRequest);

	public Course updateCourse(@Valid CourseUpdateRequest courseUpdateRequest);

}

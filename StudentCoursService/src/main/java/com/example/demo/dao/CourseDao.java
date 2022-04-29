package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.dto.CourseDTO;
import com.example.demo.medel.Course;
import com.example.demo.request.ListPageRequest;

@Component
public interface CourseDao {

	public Course findCourseById(String id);

	public List<Course> findAll();

	public Course updateCourse(Course course);

	public Course deleteCourse(Course course);

	public List<Course> findPage(ListPageRequest request);

	public List<CourseDTO> findAllCoursesByLookup();

}

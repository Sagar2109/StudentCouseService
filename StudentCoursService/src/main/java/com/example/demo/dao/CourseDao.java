package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.medel.Course;

@Component
public interface CourseDao {

	Course findCourseById(String id);

	public List<Course> findAll();

	public Course updateCourse(Course course);

}

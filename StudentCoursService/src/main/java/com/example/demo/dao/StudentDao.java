package com.example.demo.dao;

import java.util.List;


import org.springframework.stereotype.Component;

import com.example.demo.medel.Course;
import com.example.demo.medel.Student;
import com.example.demo.request.ListPageRequest;

@Component
public interface StudentDao {

	List<Student> findAllData();

	String deleteData(String id);

	Object insertData(Student student);

	Student updateData(Student student);

	Student findByIdData(String id);

	Student findByEmailData(String email);
   

	List<Student> findAllByCoursesData(List<String> courseid);

	boolean isEmailExists(String emai);

	List<Student> findAllByCoursesByStudet(String courseid);

	static List<Course> findPage(ListPageRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}

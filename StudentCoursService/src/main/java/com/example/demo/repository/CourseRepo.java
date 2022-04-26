package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.medel.Course;

@Repository
public interface CourseRepo<T> extends MongoRepository<Course, String> {

	@Query(value="{'_id':{$in:?0}}")
	public List<Course> findAllCourseByIds(List<String> list);

	
		
	
	
}

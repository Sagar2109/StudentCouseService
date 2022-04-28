package com.example.demo.daoImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.example.demo.dao.CourseDao;
import com.example.demo.medel.Course;
import com.example.demo.repository.CourseRepo;

@Component
public class CourseDaoImpl implements CourseDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private CourseRepo courseRpo;

	@Override
	public Course findCourseById(String id) {

		return courseRpo.findCourseById(id);
	}

	@Override
	public List<Course> findAll() {

		Criteria criteria = Criteria.where("suspended").is(false);
		Query query = Query.query(criteria);
		return mongoTemplate.find(query, Course.class, "CourseInfo");

	}

	@Override
	public Course updateCourse(Course course) {

		Query query = Query.query(Criteria.where("cid").is(course.getCid()));
		Update update = new Update().set("cname", course.getCname()).set("cdesc", course.getCdesc())
				.set("modifiedAt", new Date()).set("createdBy", course.getCreatedBy());
		return mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), Course.class);

	}

}

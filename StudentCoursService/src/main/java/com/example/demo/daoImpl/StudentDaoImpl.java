package com.example.demo.daoImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;

@Service
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Student> findAllData() {
		return mongoTemplate.findAll(Student.class, "StudentInfo");

	}

	@Override
	public Object insertData(Student student) {

		return mongoTemplate.save(student);

	}

	@Override
	public Student updateData(Student student) {

		Query query = Query.query(Criteria.where("id").is(student.getId()));
		Update update = new Update().set("name", student.getName()).set("contactNo", student.getContactNo())
				.set("modifiedAt", new Date());

		return mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true),
				Student.class);

	}

	@Override
	public Student findByIdData(String id) {

		// return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(new
		// ObjectId(id))), Student.class);

		MatchOperation ss = Aggregation.match(Criteria.where("_id").is(id));

		return mongoTemplate.aggregate(Aggregation.newAggregation(ss), "StudentInfo", Student.class)
				.getUniqueMappedResult();
	}

	@Override
	public Student findByEmailData(String email) {
		return mongoTemplate.findOne(Query.query(Criteria.where("email").is(email)), Student.class);

	}

	@Override
	public List<Student> findAllByCoursesData(List<String> courseid) {
		Query query = new Query(Criteria.where("courseIds").in(courseid));

		return mongoTemplate.find(query, Student.class);

	}

	@Override
	public List<Student> findAllByCoursesByStudet(String courseid) {
		Query query = new Query(Criteria.where("courseIds").in(courseid));

		return mongoTemplate.find(query, Student.class);

	}

	@Override
	public boolean isEmailExists(String emai) {
		if (mongoTemplate.exists(Query.query(Criteria.where("email").is(emai)), Student.class))
			return true;
		else
			return false;

	}

}

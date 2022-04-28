package com.example.demo.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.dao.StudentDao;
import com.example.demo.medel.Student;

@Service
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired

	@Override
	public List<Student> findAllData() {
		return mongoTemplate.findAll(Student.class, "StudentInfo");

	}

	@Override
	public String deleteData(String id) {

		Student student = new Student();
		student.setId(id);
		mongoTemplate.remove(student);
		return "Deleted Successfully";
	}

	@Override
	public Object insertData(Student student) {
       
		mongoTemplate.save(student);
		return student;

	}

	@Override
	public Student updateData(Student student) {

		mongoTemplate.save(student);
		return student;

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

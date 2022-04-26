package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.dao.StudentDao;
import com.example.demo.dto.CourseDTO;
import com.example.demo.medel.Course;
import com.example.demo.medel.Student;
import com.example.demo.reponse.CourseStudentDetailResponse;
import com.example.demo.repository.CourseRepo;
import com.example.demo.request.ListPageRequest;

@Service

public class CourseServiceImpl implements CourseService {

	List<Course> list;

	@Autowired
	private CourseRepo<Course> courseRepo;
	@Autowired
	private StudentDao stuentDao;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Course> findAll() {
		list = courseRepo.findAll();
		return list;
	}

	@Override
	public Course find(String id) {
		Optional<Course> ob1;
		ob1 = courseRepo.findById(id);
		if(ob1.isPresent())
			return ob1.get();
		return null;
       
	}

	@Override
	public String delete(String id) {

		courseRepo.deleteById(id);
		return "Record deleted";

	}

	@Override
	public Object insert(Course course) {
		if (isCourseNameExists(course.getCname()))
			return "Couse Name Already Taken";
		else
			courseRepo.save(course);
		return course;

	}

	@Override
	public boolean isCourseNameExists(String cname) {
		if (mongoTemplate.exists(Query.query(Criteria.where("cname").is(cname)), Course.class))
			return true;
		else
			return false;

	}

	@Override
	public Course update(Course course) {

		String id = course.getCid();
		if (courseRepo.existsById(id)) {
			courseRepo.deleteById(id);
			courseRepo.save(course);
		}
		return course;

	}

	@Override
	public List<Course> findAllCourses(List<String> list) {

		return courseRepo.findAllCourseByIds(list);

	}

	@Override

	public CourseStudentDetailResponse findCourseStudentDetails(String id) {

		Course course = find(id);

		List<Student> students = stuentDao.findAllByCoursesByStudet(id);

		CourseStudentDetailResponse courseStud = new CourseStudentDetailResponse();
		courseStud.set_id(course.getCid());
		courseStud.setName(course.getCname());
		courseStud.setDesc(course.getCdesc());
		courseStud.setStudents(students);

		return courseStud;
	}

	@Override
	public List<Course> findPage(ListPageRequest request) {

		Criteria cnm = Criteria.where("cname").regex(request.getSearchText(), "i");
		Criteria cds = Criteria.where("cdesc").regex(request.getSearchText(), "i");

		Query query = new Query(new Criteria().orOperator(cnm, cds));
		query.skip(request.getPage() * request.getTotalInList()).limit((int) request.getTotalInList());

		String s[] = request.getFields();

		query.fields().include(s).include("_class");

		list = mongoTemplate.find(query, Course.class, "CourseInfo");

		return list;
	}

	@Override
	public List<CourseDTO> findAllCoursesByLookup() {
        
		AggregationOperation documentId = new AggregationOperation() {
			@Override
			public Document toDocument(AggregationOperationContext context) {

				return new Document("$addFields", new Document("cid", new Document("$toString", "$_id")));
			}
		};

		LookupOperation ss = Aggregation.lookup("StudentInfo", "cid", "courseIds", "students");

		List<CourseDTO> list = mongoTemplate
				.aggregate(Aggregation.newAggregation(documentId, ss), "CourseInfo", CourseDTO.class)
				.getMappedResults();

		return list;
	}

	public CourseDTO CourseToCourseDTO(Course course) {
		CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);

		return courseDTO;
	}

	public Course CourseDTOToCourse(CourseDTO courseDTO) {
		Course course = modelMapper.map(courseDTO, Course.class);
		return course;
	}

}
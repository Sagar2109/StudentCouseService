package com.example.demo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CourseDao;
import com.example.demo.dao.StudentDao;
import com.example.demo.dto.CourseDTO;
import com.example.demo.medel.Course;
import com.example.demo.medel.Student;
import com.example.demo.reponse.CourseStudentDetailResponse;
import com.example.demo.repository.CourseRepo;
import com.example.demo.request.CourseDeleteRequest;
import com.example.demo.request.CourseUpdateRequest;
import com.example.demo.request.ListPageRequest;

@Service

public class CourseServiceImpl implements CourseService {

	List<Course> list;

	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private StudentDao stuentDao;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Course> findAll() {
		list = courseDao.findAll();
		return list;
	}

	@Override
	public Course findCourseById(String id) {

		return courseDao.findCourseById(id);

	}

	@Override
	public Object insert(Course course) {

		if (isCourseNameExists(course.getCname()))
			return "Couse Name Already Taken";
		else {
			course.setSupended(false);
			return courseRepo.save(course);

		}

	}

	@Override
	public boolean isCourseNameExists(String cname) {
		if (mongoTemplate.exists(Query.query(Criteria.where("cname").is(cname)), Course.class))
			return true;
		else
			return false;

	}

	@Override
	public Course updateCourse(CourseUpdateRequest courseUpdateRequest) {

		Course course = modelMapper.map(courseUpdateRequest, Course.class);

		return courseDao.updateCourse(course);

	}

	@Override
	public List<Course> findAllCourses(List<String> list) {

		return courseRepo.findAllCourseByIds(list);

	}

	@Override

	public CourseStudentDetailResponse findCourseStudentDetails(String id) {

		Course course = findCourseById(id);

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

		return courseDao.findPage(request);

	}

	@Override
	public List<CourseDTO> findAllCoursesByLookup() {

		return courseDao.findAllCoursesByLookup();

	}

	public CourseDTO CourseToCourseDTO(Course course) {
		CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);

		return courseDTO;
	}

	public Course CourseDTOToCourse(CourseDTO courseDTO) {
		Course course = modelMapper.map(courseDTO, Course.class);
		return course;
	}

	@Override
	public Course deleteCourse(CourseDeleteRequest request) {

		Course course = courseDao.findCourseById(request.getCid());

		return courseDao.deleteCourse(course);

	}

}
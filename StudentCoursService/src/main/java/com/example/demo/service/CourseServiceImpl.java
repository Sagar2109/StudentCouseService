package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dao.CourseDao;
import com.example.demo.dao.StudentDao;
import com.example.demo.dto.CourseDTO;
import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.reponse.CourseStudentDetailResponse;
import com.example.demo.reponse.CourseWithUserResponse;
import com.example.demo.reponse.ListCoursesResponse;
import com.example.demo.reponse.UserResponse;
import com.example.demo.repository.CourseRepo;
import com.example.demo.request.CourseAddRequest;
import com.example.demo.request.CourseDeleteRequest;
import com.example.demo.request.CourseUpdateRequest;
import com.example.demo.request.ListPageRequest;

@Service

public class CourseServiceImpl implements CourseService {

	private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

	List<Course> list;

	@Value("${usersBy.CoursePage}")
	private String getUserIdsURL;

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

	@Value("${userService.user.get}")
	private String getUserURL;

	@Override
	public List<Course> findAll() {
		list = courseDao.findAll();
		return list;
	}

	@Override
	public CourseWithUserResponse findCourseById(String id) {
		Course course = courseDao.findCourseById(id);
		CourseWithUserResponse response = modelMapper.map(course, CourseWithUserResponse.class);
		response.setCreatedByUser(findUserById(course.getCreatedBy()));
		return response;
	}

	@Override
	public Course insert(CourseAddRequest request) {

		if (isCourseNameExists(request.getCname()))
			return null;
		else {
			Course course = modelMapper.map(request, Course.class);
			course.setSuspended(false);
			course.setCreatedAt(new Date());
			course.setModifiedAt(new Date());
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

		Course course = null; // findCourseById(id);

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

	public UserResponse findUserById(String id) {
		HttpHeaders headers = new HttpHeaders();

		Map<String, Object> map = new HashMap<>();

		getUserURL += "?id=" + id;

		headers.setAcceptLanguageAsLocales(Arrays.asList(Locale.ENGLISH));
		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> surveyResponse = restTemplate.exchange(getUserURL, HttpMethod.GET, httpEntity, Map.class);
		System.out.println(surveyResponse);
		log.info("Exception Inside CourseServiceImpl in Api findUserById(...)");

		return modelMapper.map(surveyResponse.getBody().get("data"), UserResponse.class);

	}

	@Override
	public List<ListCoursesResponse> findAllCoursesBycreatedBy(String createdBy) {
		List<Course> courses = courseDao.findAllCoursesBycreatedBy(createdBy);
		List<ListCoursesResponse> list = modelMapper.map(courses, new TypeToken<List<ListCoursesResponse>>() {
		}.getType());

		return list;
	}

	@Override
	public List<CourseWithUserResponse> findCoursewithUser(ListPageRequest request) throws IllegalAccessException {
		List<Course> list = findPage(request);

		if (list == null) {
			new ArrayList<>();
		}
		Set<String> createdBy = list.stream().map(l -> l.getCreatedBy()).collect(Collectors.toSet());

		List<UserResponse> users = findUsersByCourse(createdBy);
		List<CourseWithUserResponse> coursewithuser = modelMapper.map(list,
				new TypeToken<List<CourseWithUserResponse>>() {
				}.getType());

		for (int i = 0; i < coursewithuser.size(); i++) {

			for (UserResponse ur : users) {

				if (ur.getId().equals(coursewithuser.get(i).getCreatedBy())) {
					coursewithuser.get(i).setCreatedByUser(ur);
					break;
				}
			}
		}
		return coursewithuser;
	}

	private List<UserResponse> findUsersByCourse(Set<String> createdBy) {

		HttpHeaders headers = new HttpHeaders();
		Map<String, Object> map = new HashMap<>();
		map.put("id", createdBy);

		headers.setAcceptLanguageAsLocales(Arrays.asList(Locale.ENGLISH));
		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> surveyResponse = restTemplate.exchange(getUserIdsURL, HttpMethod.POST, httpEntity,
				Map.class);
		log.info("Exception Inside CourseServiceImpl in Api findUsersByCourse(...)");
		return modelMapper.map(surveyResponse.getBody().get("data"), new TypeToken<List<UserResponse>>() {
		}.getType());
		// new TypeToken<List<ListCoursesResponse>>() {}.getType();
	}
}

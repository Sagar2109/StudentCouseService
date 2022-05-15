package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CourseDTO;
import com.example.demo.model.Course;
import com.example.demo.reponse.CourseStudentDetailResponse;
import com.example.demo.reponse.CourseWithUserResponse;
import com.example.demo.reponse.ListCoursesResponse;
import com.example.demo.request.CourseDeleteRequest;
import com.example.demo.request.CourseUpdateRequest;
import com.example.demo.request.ListPageRequest;
import com.example.demo.service.CourseService;
import com.example.demo.util.Response;
import com.example.demo.util.Utils;

@RestController
@RequestMapping("/course")
public class CourseController {

	private static final Logger log = LoggerFactory.getLogger(CourseController.class);
	@Autowired
	private CourseService courseService;

	@GetMapping("/find-all-course")
	public List<Course> findAllCourse() {
		return courseService.findAll();

	}

	/*
	 * @GetMapping("/getsing") public ResponseEntity<Object>
	 * findCourseById(@RequestParam String cid) {
	 * 
	 * Course course = courseService.findCourseById(cid);
	 * 
	 * if (course != null) {
	 * 
	 * return ResponseEntity.ok().body(course); } else {
	 * 
	 * return ResponseEntity.notFound().build();
	 * 
	 * } }
	 */

	@GetMapping("/")
	public Object findCourseById(@RequestParam String cid) {

		CourseWithUserResponse course;
		try {

			course = courseService.findCourseById(cid);
			if (course == null) {
				throw new IllegalAccessException();
			} else {

				return Response.data(HttpStatus.OK.value(), "Ok", "Course Found", course);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.data(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", "Course Not Found", null);

		}
	}

	@PutMapping("/del")
	public Object deleteCourse(@Valid @RequestBody CourseDeleteRequest request, BindingResult bindingResult) {
		Course course = null;

		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(bindingResult),
					null);
		}
		try {

			course = courseService.deleteCourse(request);

			if (course == null)

				throw new IllegalAccessException();

			return Response.data(HttpStatus.OK.value(), "Ok", "User Deleted successfuly", course.getCid());

		} catch (Exception e) {

			return Response.data(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", "User is not found", course);

		}
	}

	@PostMapping("/insert")
	public Object insertRec(@Valid @RequestParam String authUserId, @RequestBody Course course,
			BindingResult bindingResult) {
		Object course1=null;
		
	
		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(bindingResult),
					null);
		}

		try {

			 course1=courseService.insert(course);

			if (course1 == null)
				throw new IllegalAccessException();
			
              
			return Response.data(HttpStatus.OK.value(), "Ok", "Course Added successfuly", course);
		} catch (Exception e) {
			log.info("Exception Inside CourseController in Api inserRec(...)");
			return Response.data(HttpStatus.CONFLICT.value(), "CONFLICT", "Email is already taken", course1);

		}
	

	}

	@PutMapping("/update")
	public Object updateCourse(@Valid @RequestBody CourseUpdateRequest courseUpdateRequest,
			BindingResult bindingResult) {
		Course course = null;

		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(bindingResult),
					null);
		}

		try {

			course = courseService.updateCourse(courseUpdateRequest);

			if (course == null)
				throw new IllegalAccessException();

			return Response.data(HttpStatus.OK.value(), "Ok", "Course Updated successfuly", course);
		} catch (Exception e) {

			return Response.data(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", "Course is not found", null);

		}
	}

	@GetMapping("/course-students-details")
	public CourseStudentDetailResponse courseStudentDetails(@RequestParam String id) {

		return courseService.findCourseStudentDetails(id);

	}

	/*
	 * @PostMapping("/list-page") public List<Course>
	 * listPagenation(@Valid @RequestBody ListPageRequest request) { return
	 * courseService.findPage(request); }
	 */

	/*
	 * @PostMapping("/list-page") public ResponseEntity<Object>
	 * listPagenation(@Valid @RequestBody ListPageRequest request, BindingResult
	 * bindingResult) { if (bindingResult.hasErrors()) {
	 * 
	 * return ResponseEntity.badRequest().body(bindingResult.getFieldError().
	 * getDefaultMessage()); } else {
	 * 
	 * List<Course> list = courseService.findPage(request);
	 * 
	 * return ResponseEntity.ok().body(list);
	 * 
	 * } }
	 */

	@PostMapping("/list-page")
	public Object listPagenation(@Valid @RequestBody ListPageRequest request, BindingResult bindingResult) {
		List<Course> list = null;
		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {

			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(bindingResult),
					null);

		}

		try {

			list = courseService.findPage(request);
			if (list == null) {
				throw new IllegalAccessException();
			}

			return Response.data(HttpStatus.OK.value(), "Ok", "List Found", list);

		} catch (Exception e) {

			return Response.data(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", "Record Not Found", null);

		}
	}

	@GetMapping("/course-lookup")
	public List<CourseDTO> listLookUp() {
		return courseService.findAllCoursesByLookup();
	}

	/*
	 * @GetMapping("/findAll") public List<ListCoursesResponse>
	 * findAllCourseBycreatedBy(@Valid @RequestParam String createdBy) {
	 * 
	 * return courseService.findAllCoursesBycreatedBy(createdBy);
	 * 
	 * }
	 */

	@GetMapping("/findAll")
	public Object findAllCourseBycreatedBy(@Valid @RequestParam String createdBy) {

		List<ListCoursesResponse> list = null;
		if (createdBy == null) {

			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(null), null);

		}

		try {

			list = courseService.findAllCoursesBycreatedBy(createdBy);
			if (list == null) {
				throw new IllegalAccessException();
			}

			log.info("Exception Inside CourseController in Api findAllCourseBycreatedBy(...)");

			return Response.data(HttpStatus.OK.value(), "Ok", "List Found", list);

		} catch (Exception e) {

			return Response.data(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", "Record Not Found", null);

		}

	}

	@PostMapping("/users-by-coursePage")
	public Object usersByCoursePage(@Valid @RequestBody ListPageRequest request, BindingResult bindingResult) {

		List<CourseWithUserResponse> coursewithusers = new ArrayList<>();

		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(bindingResult),
					null);
		}
		try {
			coursewithusers = courseService.findCoursewithUser(request);

			if (coursewithusers == null) {
				throw new IllegalAccessException();
			}
			log.info("Exception Inside CourseController in Api usersByCoursePage(...)");
			return Response.data(HttpStatus.OK.value(), "Ok", "Course with User List Found", coursewithusers);
		} catch (Exception e) {
			return Response.data(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", "Record Not Found", coursewithusers);
		}
	}
}

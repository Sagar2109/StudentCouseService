package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.StudentDTO;
import com.example.demo.model.Student;
import com.example.demo.request.ListCourseIdRequest;
import com.example.demo.request.StudentAddRequest;
import com.example.demo.request.StudentUpdateRequest;
import com.example.demo.service.StudentService;
import com.example.demo.util.Response;
import com.example.demo.util.Utils;

@RestController
@RequestMapping("/student")
public class StudentController {

	private static final Logger log = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	private StudentService studentService;

	@GetMapping("/findAll")
	public List<Student> findAllStudnt() {
		return studentService.findAll();
	}

	@GetMapping("/find_by_id")
	public Object findStudentById(@Valid @RequestParam String id, BindingResult bindingResult) {
		StudentDTO student1 = null;
		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(bindingResult),
					null);
		}
		try {
			student1 = studentService.findById(id);
			if (student1 == null)
				throw new IllegalAccessException();

			return Response.data(HttpStatus.OK.value(), "Ok", "Student found", student1);
		} catch (Exception e) {
			log.info("Exception Inside StudentController in Api findStudentById(...)");
			return Response.data(HttpStatus.CONFLICT.value(), "CONFLICT", "Student is not available", student1);
		}

	}

	@GetMapping("/find_by_email")
	public Object findStudentByEmail(@Valid @RequestParam String email, BindingResult bindingResult) {
		Student student1 = null;
		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(bindingResult),
					null);
		}
		try {
			student1 = studentService.findByEmail(email);
			if (student1 == null)
				throw new IllegalAccessException();

			return Response.data(HttpStatus.OK.value(), "Ok", "Student found", student1);
		} catch (Exception e) {
			log.info("Exception Inside StudentController in Api findStudentByEmail(...)");
			return Response.data(HttpStatus.CONFLICT.value(), "CONFLICT", "Student is not available", student1);
		}

	}

	@GetMapping("/find_by_courseid")
	public Object findStudentByCourseIds(@Valid @RequestBody ListCourseIdRequest request, BindingResult bindingResult) {
		
		List<Student> list = null;
		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(bindingResult),
					null);
		}
		try {
			list = studentService.findAllByCourses(request.getCourseid());
			if (list == null)
				throw new IllegalAccessException();

			return Response.data(HttpStatus.OK.value(), "Ok", "Student List found", list);
		} catch (Exception e) {
			log.info("Exception Inside StudentController in Api findStudentByCourseIds(...)");
			return Response.data(HttpStatus.CONFLICT.value(), "CONFLICT", "Student List is not available", list);
		}
	

	}

	@PostMapping("/insert_student")
	public Object insertStudent(@Valid @RequestBody StudentAddRequest request, BindingResult bindingResult) {

		Object student1 = null;

		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(bindingResult),
					null);
		}
		try {
			student1 = studentService.insert(request);
			if (student1 == null)
				throw new IllegalAccessException();

			return Response.data(HttpStatus.OK.value(), "Ok", "Student Added successfuly", student1);
		} catch (Exception e) {
			log.info("Exception Inside StudentController in Api insertStudent(...)");
			return Response.data(HttpStatus.CONFLICT.value(), "CONFLICT", "Emai is Allready taken", student1);
		}
	}

	@PutMapping("/update_student")
	public Object updateStudent(@Valid @RequestBody StudentUpdateRequest request, BindingResult bindingResult) {
		Object student1 = null;

		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(bindingResult),
					null);
		}
		try {
			student1 = studentService.update(request);
			if (student1 == null)
				throw new IllegalAccessException();

			return Response.data(HttpStatus.OK.value(), "Ok", "Student Updated successfuly", student1);
		} catch (Exception e) {
			log.info("Exception Inside StudentController in Api updateStudent(...)");
			return Response.data(HttpStatus.CONFLICT.value(), "CONFLICT", "Emai is Allready taken", student1);
		}
	}

	@DeleteMapping("/delete_student")
	public Object deleteStudent(@Valid @RequestParam String id, BindingResult bindingResult) {
		Object student1 = null;

		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
			return Response.data(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", Utils.getFieldError(bindingResult),
					null);
		}
		try {
			student1 = studentService.delete(id);
			if (student1 == null)
				throw new IllegalAccessException();

			return Response.data(HttpStatus.OK.value(), "Ok", "Student Deleted successfuly", student1);
		} catch (Exception e) {
			log.info("Exception Inside StudentController in Api deleteStudent(...)");
			return Response.data(HttpStatus.CONFLICT.value(), "CONFLICT", "Student is not available", student1);
		}

	}

}

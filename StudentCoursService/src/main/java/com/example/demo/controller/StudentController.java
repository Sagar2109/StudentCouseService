package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.StudentDTO;
import com.example.demo.medel.Student;
import com.example.demo.request.ListCourseIdRequest;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/findAll")
	public List<Student> findAllStudnt() {
		return studentService.findAll();
	}

	@GetMapping("/find_by_id")
	public StudentDTO findStudentById(@RequestParam String id) {
		return studentService.findById(id);
	}

	@GetMapping("/find_by_email")
	public Student findStudentByEmail(@RequestParam String email) {
		return studentService.findByEmail(email);

	}

	@GetMapping("/find_by_courseid")
	public List<Student> findStudentByCourseIds(@RequestBody ListCourseIdRequest  request) {
		return studentService.findAllByCourses(request.getCourseid());

	}

	@PostMapping("/insert_student")
	public Object insertStudent(@RequestBody Student student) {
		return studentService.insert(student);

	}

	@PutMapping("/update_student")
	public Student updateStudent(@RequestBody Student student) {
		return studentService.update(student);

	}

	@DeleteMapping("/delete_student")
	public String deleteStuent(@RequestParam String id) {

		return studentService.delete(id);
	}
	
}

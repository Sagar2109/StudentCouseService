package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CourseDTO;
import com.example.demo.medel.Course;
import com.example.demo.reponse.CourseStudentDetailResponse;
import com.example.demo.request.ListPageRequest;
import com.example.demo.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService service;

	@GetMapping("/retrive")
	public List<Course> getRec() {
		return service.findAll();

	}

	
	/*
	 * @GetMapping("/getsing") public Course getSingRec(@RequestParam String cid) {
	 * return service.find(cid);
	 * 
	 * }
	 */
	 
	 @GetMapping("/getsing")
	 public ResponseEntity<Object> getSingRec(@RequestParam String cid) {
	  
		 Course course= service.find(cid);
	  
		 if(course!=null)
		 {
			 System.out.println(ResponseEntity.ok().body(course).toString());
			 return ResponseEntity.ok().body(course);
		 }else
		 {
			 System.out.println(ResponseEntity.notFound().build().toString());
			 return ResponseEntity.notFound().build();
			 
		 }
	  }

	
	@DeleteMapping("/del")
	public String getDelRec(@RequestParam String cid) {
		return service.delete(cid);

	}

	@PostMapping("/insert")
	public Object insertRec(@RequestBody Course course) {

		return service.insert(course);

	}

	@PutMapping("/update")
	public Course updateRec(@RequestBody Course course) {

		return service.update(course);

	}

	@GetMapping("/course-students-details")
	public CourseStudentDetailResponse courseStudentDetails(@RequestParam String id) {

		return service.findCourseStudentDetails(id);

	}

	/*
	 * @PostMapping("/list-page") public List<Course>
	 * listPagenation(@Valid @RequestBody ListPageRequest request) { return
	 * service.findPage(request); }
	 */
	
	@PostMapping("/list-page")
	public ResponseEntity<Object> listPagenation(@Valid @RequestBody ListPageRequest request, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
		{ 
			
			return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
		}else
		{
			
		   List<Course> list=service.findPage(request);
		   
		  return  ResponseEntity.ok().body(list);
		    
		}
	}

	
	@GetMapping("/course-lookup")
	public List<CourseDTO> listLookUp() {
		return service.findAllCoursesByLookup();
	}
}

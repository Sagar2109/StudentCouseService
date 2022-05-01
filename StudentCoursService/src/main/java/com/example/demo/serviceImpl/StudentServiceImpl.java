package com.example.demo.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.StudentDao;
import com.example.demo.dto.StudentDTO;
import com.example.demo.model.Student;
import com.example.demo.service.CourseService;
import com.example.demo.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CourseService courseService;

	@Override
	public List<Student> findAll() {

		return studentDao.findAllData();

	}

	@Override
	public String delete(String id) {

		studentDao.deleteData(id);
		return "Deleted Successfully";
	}

	@Override
	public Object insert(Student student) {
		if (studentDao.isEmailExists(student.getEmail()))
			return "Emai is Allready taken";
		else
			return studentDao.insertData(student);
	
			
	}

	@Override
	public Student update(Student student) {

		studentDao.updateData(student);
		return student;

	}

	@Override
	public StudentDTO findById(String id) {
		Student student = studentDao.findByIdData(id);
		StudentDTO studentDTO = studentToStudentDto(student);
		studentDTO.setCourses(courseService.findAllCourses(student.getCourseIds()));
		return studentDTO;

	}

	@Override
	public Student findByEmail(String email) {

		return studentDao.findByEmailData(email);

	}

	@Override
	public List<Student> findAllByCourses(List<String> courseid) {
		return studentDao.findAllByCoursesData(courseid);
	}

	public StudentDTO studentToStudentDto(Student student) {

		StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
		return studentDTO;
	}

	public Student studentDTOToStudent(StudentDTO studentDTO) {

		Student student = modelMapper.map(studentDTO, Student.class);
		return student;
	}
}

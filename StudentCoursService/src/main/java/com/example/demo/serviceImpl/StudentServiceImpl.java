package com.example.demo.serviceImpl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.StudentDao;
import com.example.demo.dto.StudentDTO;
import com.example.demo.model.Student;
import com.example.demo.request.StudentAddRequest;
import com.example.demo.request.StudentUpdateRequest;
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
	public Student delete(String id) {
		Student s1 = studentDao.findByIdData(id);

		if (s1 != null && (s1.getSuspended() == false)) {
			s1.setModifiedAt(new Date());
			s1.setSuspended(true);
			return studentDao.updateData(s1);

		} else {
			return null;
		}
	}

	@Override
	public Object insert(StudentAddRequest request) {
		if (studentDao.isEmailExists(request.getEmail()))
			return null;
		else {
			Student student=modelMapper.map(request, Student.class);
			student.setSuspended(false);
			student.setCreatedAt(new Date());
			student.setModifiedAt(new Date());
			return studentDao.insertData(student);
		}

	}

	@Override
	public Student update(StudentUpdateRequest request) {

	return	studentDao.updateData(modelMapper.map(request, Student.class));
		

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

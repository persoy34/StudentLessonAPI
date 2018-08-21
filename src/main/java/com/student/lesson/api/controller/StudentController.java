package com.student.lesson.api.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;

import com.student.lesson.api.dao.LessonDAO;
import com.student.lesson.api.dao.StudentDAO;
import com.student.lesson.api.exception.ResourceNotFoundException;
import com.student.lesson.api.model.Lesson;
import com.student.lesson.api.model.Student;

@RestController
@RequestMapping("/student")
@Transactional
public class StudentController {
	
	@Autowired
	StudentDAO studentDao;
	
	@Autowired
	LessonDAO lessonDao;
	
	 @GetMapping(value="/{studentId}")
	public  ResponseEntity<Student> findById(@PathVariable (value = "studentId") int studentId){
		Student studentOpt= studentDao.getStudentById(studentId);
		 if (studentOpt != null) {
			 return new ResponseEntity<Student>(studentOpt, HttpStatus.OK);
		 }
		 throw new RestClientResponseException("Student with this id isn't exist", HttpStatus.NOT_FOUND.value(),"Student with this id isn't exist", null, null, null);
	 }
	 
	 @GetMapping()
	 public ResponseEntity<List<Student>> getAllStudent(){
		 return new ResponseEntity<>(studentDao.getAllStudent(),HttpStatus.OK);
	 }
	 
	 @PutMapping(value="/add-student", headers = "Accept=application/json")
	 @Transactional
	 public Student addStudent(@RequestBody Student student){
		Set<Lesson> lessons = student.getLesson();
		Set<Lesson> newLessons = new HashSet<>();
		if (lessons != null && lessons.size() > 0) {
			lessons.forEach(lesson -> {
				 Lesson lessonOpt = lessonDao.getLessonByCode(lesson.getCode());
				if (lessonOpt == null) {
					lessonOpt=lessonDao.addLesson(lesson);
				} 
				newLessons.add(lessonOpt);
			});
			student.setLesson(newLessons);
		}
		student = studentDao.addStudent(student);
		return student;
	 }
	 
	 @PostMapping(value="/update-student")
	 public ResponseEntity<Student> updateStudent(@RequestBody Student student){
			Set<Lesson> lessons = student.getLesson();
			if (lessons != null && lessons.size() > 0) {
				lessons.forEach(lesson -> {
					 Lesson lessonOpt = lessonDao.getLessonByCode(lesson.getCode());
					if (lessonOpt == null) {
						lessonOpt=lessonDao.addLesson(lesson);
					} 
					lesson.setId(lessonOpt.getId());
					lesson.setCode(lessonOpt.getCode());
					lesson.setName(lessonOpt.getName());
					lesson.setCredit(lessonOpt.getCredit());
				});
				
			}
			try {
				studentDao.getStudentById(student.getId());
				student = studentDao.updateStudent(student);
			} catch (Exception e) {
				student = studentDao.addStudent(student);
			}
		 return new ResponseEntity<Student>(student, HttpStatus.OK);
	 }
	 
	 @DeleteMapping(value = "/delete-student")
	 public ResponseEntity<?> deleteStudent(@RequestBody Student student){
		 try {
		   if (student.getId() != 0) {
			   student = studentDao.getStudentById(student.getId());
		   }
		   else {
			   student = studentDao.findStudent(student);
		   }
		   studentDao.deleteStudent(student);
		   return ResponseEntity.ok().build();
		 }
		 catch(Exception e) {
			 throw new ResourceNotFoundException("Student not found");
		 }
	 }
}

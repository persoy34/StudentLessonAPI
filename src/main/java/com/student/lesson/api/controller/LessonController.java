package com.student.lesson.api.controller;

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
@RequestMapping("/lesson")
@Transactional
public class LessonController {
	
	@Autowired
	LessonDAO lessonDao;
	
	@Autowired
	StudentDAO studentDao;
	
	@GetMapping(value="/{lessonCode}")
	public ResponseEntity<Lesson> getLessonCode(@PathVariable(value ="lessonCode") String lessonCode ){
		Lesson lesson=lessonDao.getLessonByCode(lessonCode);
		if (lesson != null) {
			return new ResponseEntity<>(lesson, HttpStatus.OK);
		}
		 throw new RestClientResponseException("Lesson with code:"+lessonCode+" isn't exist", HttpStatus.NOT_FOUND.value(),
				 "Lesson isn't exist", null, null, null);
	}
	
	@GetMapping()
	public ResponseEntity<List<Lesson>> getAllLessons(){
		return new ResponseEntity<List<Lesson>>(lessonDao.getAllLesson(), HttpStatus.OK);
	}
	
	 @PutMapping(value="/add-lesson")
	 public ResponseEntity<Lesson> addLesson(@RequestBody Lesson lesson){
	    Set<Student> students = lesson.getStudents();	
	    if (students != null) {
	    	students.forEach(student -> {
	    		Student student1;
	    		try {
	    		 student1 = studentDao.findStudent(student);
	    		 student.setId(student1.getId());
	    		 student.setAge(student1.getAge());
	    		 student.setName(student1.getName());
	    		}
	    		catch (Exception e) {
	    		}
	    		
	    	});
	    }
		return new ResponseEntity<Lesson>(lessonDao.addLesson(lesson),HttpStatus.OK);
	 }
	 
	 @PostMapping(value="/update-lesson")
	 public ResponseEntity<Lesson> updateLesson(@RequestBody Lesson lesson){
		 lessonDao.updateLesson(lesson);
		 return new ResponseEntity<Lesson>(lesson, HttpStatus.OK);
	 }
	 
	 @DeleteMapping(value = "/delete-lesson")
	 public ResponseEntity<?> deleteLesson(@RequestBody Lesson lesson){
		try { 	
		 lesson = lessonDao.getLessonByCode(lesson.getCode());
		// lessonDao.deleteStudentLesson(lesson.getStudents());
		 lessonDao.deleteLesson(lesson);
		 return ResponseEntity.ok().build();
		}
		catch (Exception e) {
			throw new ResourceNotFoundException("Lesson with  " + lesson.getCode() + " not found");
		}
	 }


}

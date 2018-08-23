package com.student.lesson.api.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.student.lesson.api.model.Lesson;
import com.student.lesson.api.model.Student;

@Repository
@Transactional
public interface LessonDAO {
	public Lesson getLessonByCode(String lessonCode);
	public List<Lesson> getAllLesson();
	public void updateLesson(Lesson lesson);
	public void deleteLesson(Lesson lesson);
	public Lesson addLesson(Lesson lesson);
	public int deleteStudentLesson(Set<Student> studentSet);
	
}

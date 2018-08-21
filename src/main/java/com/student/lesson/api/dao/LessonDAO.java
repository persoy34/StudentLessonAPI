package com.student.lesson.api.dao;

import java.util.List;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.student.lesson.api.model.Lesson;

@Repository
@Transactional
public interface LessonDAO {
	public Lesson getLessonByCode(String lessonCode);
	public List<Lesson> getAllLesson();
	public void updateLesson(Lesson lesson);
	public void deleteLesson(String lessonCode);
	public Lesson addLesson(Lesson lesson);
	
}

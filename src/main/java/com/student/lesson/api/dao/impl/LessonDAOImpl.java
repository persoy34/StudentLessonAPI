package com.student.lesson.api.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import com.student.lesson.api.dao.LessonDAO;
import com.student.lesson.api.exception.ResourceNotFoundException;
import com.student.lesson.api.model.Lesson;
import com.student.lesson.api.model.Student;

@Repository
@Transactional
public class LessonDAOImpl implements LessonDAO {

	@PersistenceContext
	private EntityManager entityManager;


	public LessonDAOImpl() {

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Lesson getLessonByCode(String lessonCode) {
		Lesson lesson = null;
		try {
			Query query = entityManager.createQuery("select l from  Lesson l where l.code=:code");
			query.setParameter("code", lessonCode);
			lesson = (Lesson) query.getSingleResult();
		} catch (NoResultException e) {
			throw new RestClientException("Lesson with code "+lessonCode+" doesn't exist");
		}
		finally {
			entityManager.close();
		}
		return lesson;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Lesson> getAllLesson() {
		List<Lesson> lessonList = null;
		try {
			Query query = entityManager.createQuery("select l from  Lesson ");
			lessonList = query.getResultList();
		} catch (NoResultException e) {
			lessonList = new ArrayList<>();
		}
		finally {
			entityManager.close();
		}
		return lessonList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLesson(Lesson lesson) {
		entityManager.merge(lesson);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLesson(Lesson lesson) {
		try {
			entityManager.remove(lesson);
		} catch (NoResultException e) {
			throw new ResourceNotFoundException("Lesson doesn't exist");
		} finally {
			entityManager.close();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteStudentLesson(Set<Student> studentSet) {
		for (Student student : studentSet) {
			Query query = entityManager.createQuery("delete from  Student_Lesson where  student_id = ?");
			query.setParameter(1, student.getId());
			int result = query.executeUpdate();
			entityManager.close();
		}
		return 0;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Lesson addLesson(Lesson lesson) {
		lesson=entityManager.merge(lesson);
		entityManager.close();
		return lesson;
	}

}

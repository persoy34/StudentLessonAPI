package com.student.lesson.api.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.student.lesson.api.dao.LessonDAO;
import com.student.lesson.api.exception.ResourceNotFoundException;
import com.student.lesson.api.model.Lesson;

@Repository
@Transactional
public class LessonDAOImpl implements LessonDAO {

	@PersistenceContext
	private EntityManager entityManager;


	public LessonDAOImpl() {

	}

	@Autowired
	public LessonDAOImpl(EntityManagerFactory factory) {
		// if(factory.unwrap(SessionFactory.class) == null){
		// throw new NullPointerException("factory is not a hibernate factory");
		// }
		// this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	@Transactional
	public Lesson getLessonByCode(String lessonCode) {
		Lesson lesson = null;
		try {
			Query query = entityManager.createQuery("select l from  Lesson l where l.code=:code");
			query.setParameter("code", lessonCode);
			lesson = (Lesson) query.getSingleResult();
		} catch (NoResultException e) {
			
		}
		finally {
		//	entityManager.close();
		}
		return lesson;
	}

	@Override
	@Transactional
	public List<Lesson> getAllLesson() {
		List<Lesson> lessonList = null;
		try {
			Query query = entityManager.createQuery("select l from  Lesson ");
			lessonList = query.getResultList();
		} catch (NoResultException e) {
			lessonList = new ArrayList<>();
		}
		finally {
			//entityManager.close();
		}
		return lessonList;
	}

	@Override
	@Transactional
	public void updateLesson(Lesson lesson) {
		entityManager.merge(lesson);
	}

	@Override
	@Transactional
	public void deleteLesson(String lessonCode) {
		Query query = entityManager.createQuery("select l from  Lesson l where l.code=:code");
		query.setParameter("code", lessonCode);
		try {
			Lesson lesson = (Lesson) query.getSingleResult();
			entityManager.remove(lesson);
		} catch (NoResultException e) {
			throw new ResourceNotFoundException("Lesson doesn't exist");
		} finally {
			entityManager.close();
		}
	}

	@Override
	public Lesson addLesson(Lesson lesson) {
		lesson=entityManager.merge(lesson);
		entityManager.close();
		return lesson;
	}

}

package com.student.lesson.api.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.student.lesson.api.dao.StudentDAO;
import com.student.lesson.api.exception.ResourceNotFoundException;
import com.student.lesson.api.model.Student;

@Repository
@Transactional
public class StudentDAOImpl implements StudentDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public StudentDAOImpl() {
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Student getStudentById(int studentId) {
		Query query = entityManager.createQuery("select s from  Student s where s.id=:student_id");
		query.setParameter("student_id", studentId);
		Student student = null;
		try {
			student = (Student) query.getSingleResult();
		} catch (NoResultException e) {
			throw new ResourceNotFoundException("Student doesn't exist");
		} finally {
		}
		return student;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Student> getAllStudent() {

		Query query = entityManager.createQuery("select s from  Student s ");
		List<Student> studentList = null;
		try {
			studentList = (List<Student>) query.getResultList();
		} catch (NoResultException e) {
			throw new ResourceNotFoundException("Student doesn't exist");
		} finally {
			entityManager.close();
		}
		return studentList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Student updateStudent(Student student) {
		student = entityManager.merge(student);
		entityManager.close();
		return student;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteStudent(Student student) {
		
		try {
			entityManager.remove(student);
		} catch (NoResultException e) {
			throw new ResourceNotFoundException("Student doesn't exist");
		} finally {
			//entityManager.close();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Student addStudent(Student student) {

		entityManager.merge(student);
		entityManager.close();
		return student;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Student findStudent(Student student) {
		Query query = entityManager.createQuery("select s from  Student s where s.name=:name and s.age=:age");
		query.setParameter("name", student.getName());
		query.setParameter("age", student.getAge());
		try {
			student = (Student) query.getSingleResult();
		} catch (NoResultException e) {
			throw new ResourceNotFoundException("Student doesn't exist");
		} finally {
			//entityManager.close();
		}		
		return student;
	}
}

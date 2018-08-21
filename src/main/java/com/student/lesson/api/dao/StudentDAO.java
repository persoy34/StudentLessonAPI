package com.student.lesson.api.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.student.lesson.api.model.Student;

@Repository
@Transactional
public interface StudentDAO {
	public Student getStudentById(int studentId);
	public List<Student> getAllStudent();
	public Student updateStudent(Student student);
	public void deleteStudent(Student student);
	public Student addStudent(Student student);
	public Student findStudent(Student student) ;
}

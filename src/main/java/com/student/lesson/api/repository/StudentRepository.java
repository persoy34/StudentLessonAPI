package com.student.lesson.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.lesson.api.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	

}

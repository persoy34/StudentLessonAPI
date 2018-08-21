package com.student.lesson.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.lesson.api.model.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
 public	Optional<Lesson> findByCode(String code);

}
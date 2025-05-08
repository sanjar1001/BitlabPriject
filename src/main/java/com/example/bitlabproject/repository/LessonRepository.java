package com.example.bitlabproject.repository;

import com.example.bitlabproject.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}

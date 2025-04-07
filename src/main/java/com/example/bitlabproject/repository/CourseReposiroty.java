package com.example.bitlabproject.repository;

import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseReposiroty extends JpaRepository<Course, Long> {

}

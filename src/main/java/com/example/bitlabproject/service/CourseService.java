package com.example.bitlabproject.service;

import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface CourseService {

    ResponseEntity<CourseDto> getCourseById(long id) throws Exception;

    ResponseEntity<List<CourseDto>> getAllCourses() throws Exception;

    ResponseEntity<CourseDto>  createCourse(CourseDto courseDto) throws Exception;

    ResponseEntity<CourseDto>  updateCourse(long id, CourseDto courseDto) throws Exception;

    ResponseEntity<?> deleteCourse(long id) throws Exception;


}

package com.example.bitlabproject.service;

import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public interface CourseService {

    CourseDto getCourseById(long id) throws Exception;

    Course createCourse(CourseDto courseDto) throws Exception;

    CourseDto updateCourse(long id, CourseDto courseDto) throws Exception;


}

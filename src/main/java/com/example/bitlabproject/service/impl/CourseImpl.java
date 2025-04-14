package com.example.bitlabproject.service.impl;

import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.exception.NotFoundException;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.CourseReposiroty;
import com.example.bitlabproject.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class CourseImpl implements CourseService {

    private final CourseReposiroty courseReposiroty;
    private final EntityMapping entityMapping;

    public CourseDto getCourseById(long id) throws Exception {

            Course course = courseReposiroty.findById(id)
                    .orElseThrow(() -> new NotFoundException("Не найдено"));
            return entityMapping.toDto(course);

    }

    @Override
    public Course createCourse(CourseDto courseDto) throws Exception {

        LocalDateTime now = LocalDateTime.now();
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setCreatedTime(now);

        return courseReposiroty.save(course);
    }

    @Override
    public CourseDto updateCourse(long id, CourseDto courseDto) throws Exception {

        Course course = courseReposiroty.findById(id)
                .orElseThrow(() -> new RuntimeException("Курс нету"));

        if (courseDto.getName() != null) {
            course.setName(courseDto.getName());
        }
        if (courseDto.getDescription() != null) {
            course.setDescription(courseDto.getDescription());
        }
        LocalDateTime now = LocalDateTime.now();
        course.setUpdatedTime(now);

        return entityMapping.toDto(courseReposiroty.save(course));


    }



}

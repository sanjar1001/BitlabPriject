package com.example.bitlabproject.service.impl;

import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.exception.NotFoundException;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.CourseReposiroty;
import com.example.bitlabproject.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class CourseImpl implements CourseService {

    private final CourseReposiroty courseReposiroty;
    private final EntityMapping entityMapping;
    @Override
    public ResponseEntity<?> getCourseById(long id) throws Exception {

        if (id <= 0) {
            throw new IllegalArgumentException("Неправильный ID. Он должен быть больше 0.");
        }

        Course course = courseReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс с id " + id + " не найден"));

        CourseDto courseDto = entityMapping.toDto(course);
        return ResponseEntity.ok(courseDto);
    }

    @Override
    public ResponseEntity<?> createCourse(@Valid CourseDto courseDto) throws Exception {

        if (courseDto == null) {
            throw new IllegalArgumentException("Данные курса не могут быть пустыми");
        }

        LocalDateTime now = LocalDateTime.now();
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setCreatedTime(now);

        Course savedCourse = courseReposiroty.save(course);
        CourseDto savedCourseDto = entityMapping.toDto(savedCourse);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourseDto);
    }

    @Override
    public ResponseEntity<?> updateCourse(long id, @Valid CourseDto courseDto) throws Exception {

        if (id <= 0) {
            throw new IllegalArgumentException("Неправильный ID. Он должен быть больше 0.");
        }

        Course course = courseReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс с id " + id + " не найден"));

        if (courseDto.getName() != null) {
            course.setName(courseDto.getName());
        }
        if (courseDto.getDescription() != null) {
            course.setDescription(courseDto.getDescription());
        }

        LocalDateTime now = LocalDateTime.now();
        course.setUpdatedTime(now);

        Course updatedCourse = courseReposiroty.save(course);
        CourseDto updatedCourseDto = entityMapping.toDto(updatedCourse);

        return ResponseEntity.ok(updatedCourseDto);
    }

    public ResponseEntity<?> deleteCourse(long id) throws Exception {

        Course course = courseReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Глава не найдена с id: " + id));

        courseReposiroty.deleteById(id);

        return ResponseEntity.ok(course);
    }




}

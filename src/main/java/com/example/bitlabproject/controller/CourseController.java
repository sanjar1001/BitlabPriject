package com.example.bitlabproject.controller;

import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.CourseReposiroty;
import com.example.bitlabproject.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseReposiroty courseReposiroty;

    private final CourseService courseService;
    private final EntityMapping entityMapping;

    @GetMapping("/course/get/list")
    public List<Course> getCourseList() throws Exception {
        return courseReposiroty.findAll();
    }

    @GetMapping("/course/get/{id}")
    public ResponseEntity<?> getCourse(@PathVariable long id) throws Exception {

        return courseService.getCourseById(id);
    }

    @PostMapping("/course/create")
    public ResponseEntity<?>  addCourse(@RequestBody CourseDto courseDto) throws Exception {

        return courseService.createCourse(courseDto);

    }

    @PatchMapping("/course/update/{id}")
    public ResponseEntity<?>  updateCourse(@PathVariable long id, @RequestBody CourseDto courseDto) throws Exception {
        return courseService.updateCourse(id,courseDto);
    }

    @DeleteMapping("/course/delete/{id}")
    public ResponseEntity<?>  deleteCourse(@PathVariable long id) throws Exception {
        return courseService.deleteCourse(id);
    }

}

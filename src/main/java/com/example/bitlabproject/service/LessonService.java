package com.example.bitlabproject.service;

import com.example.bitlabproject.dto.LessonDto;
import com.example.bitlabproject.entity.Lesson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LessonService {

    ResponseEntity<LessonDto> createLesson(long id, LessonDto lessonDto);

    ResponseEntity<LessonDto> findById(long id);

    ResponseEntity<?> deleteLesson(long id);

    ResponseEntity<LessonDto> updateLesson(long id, LessonDto lessonDto);
}


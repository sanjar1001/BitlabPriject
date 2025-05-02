package com.example.bitlabproject.service;

import com.example.bitlabproject.dto.LessonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LessonService {

    ResponseEntity<?> createLesson(long id, LessonDto lessonDto);

    ResponseEntity<?> findById(long id);

    ResponseEntity<?> deleteLesson(long id);

    ResponseEntity<?> updateLesson(long id, LessonDto lessonDto);
}


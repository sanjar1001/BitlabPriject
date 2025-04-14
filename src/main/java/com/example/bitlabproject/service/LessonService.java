package com.example.bitlabproject.service;

import com.example.bitlabproject.dto.LessonDto;
import org.springframework.stereotype.Service;

@Service
public interface LessonService {

    LessonDto createLesson(long id, LessonDto lessonDto);

    LessonDto findById(long id);

    void deleteLesson(long id);

    LessonDto updateLesson(long id, LessonDto lessonDto);
}


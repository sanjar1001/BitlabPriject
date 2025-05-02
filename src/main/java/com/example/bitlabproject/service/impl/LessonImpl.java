package com.example.bitlabproject.service.impl;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.dto.LessonDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.entity.Lesson;
import com.example.bitlabproject.exception.NotFoundException;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.ChapterReposiroty;
import com.example.bitlabproject.repository.LessonReposiroty;
import com.example.bitlabproject.service.LessonService;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LessonImpl implements LessonService {

    private final LessonReposiroty lessonReposiroty;
    private final ChapterReposiroty chapterReposiroty;
    private final EntityMapping entityMapping;


    @Override
    public ResponseEntity<?> createLesson(long id, @Valid LessonDto lessonDto) {

        if (id <= 0) {
            throw new IllegalArgumentException("Неправильный ID главы. Он должен быть больше 0.");
        }

        Chapter chapter = chapterReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Глава с id " + id + " не существует"));

        LocalDateTime now = LocalDateTime.now();
        Lesson lesson = new Lesson();
        lesson.setName(lessonDto.getName());
        lesson.setDescription(lessonDto.getDescription());
        lesson.setContent(lessonDto.getContent());
        lesson.setOrder(lessonDto.getOrder());
        lesson.setCreatedTime(now);
        lesson.setChapter(chapter);

        Lesson savedLesson = lessonReposiroty.save(lesson);
        LessonDto savedLessonDto = entityMapping.toDto(savedLesson);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedLessonDto);
    }

    @Override
    public ResponseEntity<?> findById(long id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Неправильный ID. Он должен быть больше 0.");
        }

        Lesson lesson = lessonReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Урок с id " + id + " не найден"));

        LessonDto lessonDto = entityMapping.toDto(lesson);
        return ResponseEntity.ok(lessonDto);
    }

    @Override
    public ResponseEntity<?> deleteLesson(long id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Неправильный ID. Он должен быть больше 0.");
        }

        Lesson lesson = lessonReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Урок с id " + id + " не найден"));

        lessonReposiroty.deleteById(id);

        return ResponseEntity.ok("Урок с id " + id + " успешно удален");
    }

    @Override
    public ResponseEntity<?> updateLesson(long id, @Valid LessonDto lessonDto) {

        if (id <= 0) {
            throw new IllegalArgumentException("Неправильный ID. Он должен быть больше 0.");
        }

        Lesson lesson = lessonReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Урок с id " + id + " не найден"));

        LocalDateTime now = LocalDateTime.now();

        // Обновление только переданных полей (если они не null)
        if (lessonDto.getName() != null) {
            lesson.setName(lessonDto.getName());
        }
        if (lessonDto.getDescription() != null) {
            lesson.setDescription(lessonDto.getDescription());
        }
        if (lessonDto.getContent() != null) {
            lesson.setContent(lessonDto.getContent());
        }
        if (lessonDto.getOrder() != 0) {
            lesson.setOrder(lessonDto.getOrder());
        }

        // Обновляем время изменения
        lesson.setUpdatedTime(now);

        Lesson updatedLesson = lessonReposiroty.save(lesson);
        LessonDto updatedLessonDto = entityMapping.toDto(updatedLesson);

        return ResponseEntity.ok(updatedLessonDto);
    }
}
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LessonImpl implements LessonService {

    private final Logger log = LoggerFactory.getLogger(ChapterImpl.class);
    private final LessonReposiroty lessonReposiroty;
    private final ChapterReposiroty chapterReposiroty;
    private final EntityMapping entityMapping;


    public ResponseEntity<?> createLesson(long id, @Valid LessonDto lessonDto) {
        if (id <= 0) {
            log.error("Неправильный ID главы: {}", id);
            throw new IllegalArgumentException("Неправильный ID главы. Он должен быть больше 0.");
        }

        log.info("Ищем главу с id {}", id);
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

        log.debug("Создаем новый урок: {}", lessonDto.getName(), lessonDto.getDescription(), lessonDto.getOrder()); // DEBUG-логирование данных объекта

        Lesson savedLesson = lessonReposiroty.save(lesson);
        LessonDto savedLessonDto = entityMapping.toDto(savedLesson);

        log.info("Урок с id {} успешно создан", savedLesson.getId()); // INFO-логирование успешного завершения

        return ResponseEntity.status(HttpStatus.CREATED).body(savedLessonDto);
    }

    public ResponseEntity<?> findById(long id) {
        if (id <= 0) {
            log.error("Неправильный ID: {}", id);
            throw new IllegalArgumentException("Неправильный ID. Он должен быть больше 0.");
        }

        log.info("Ищем урок с id {}", id);
        Lesson lesson = lessonReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Урок с id " + id + " не найден"));

        LessonDto lessonDto = entityMapping.toDto(lesson);

        log.info("Урок с id {} найден и возвращен", id); // INFO-логирование успешного завершения

        return ResponseEntity.ok(lessonDto);
    }

    public ResponseEntity<?> deleteLesson(long id) {
        if (id <= 0) {
            log.error("Неправильный ID для удаления: {}", id);
            throw new IllegalArgumentException("Неправильный ID. Он должен быть больше 0.");
        }

        log.info("Ищем урок для удаления с id {}", id);
        Lesson lesson = lessonReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Урок с id " + id + " не найден"));

        lessonReposiroty.deleteById(id);

        log.info("Урок с id {} успешно удален", id); // INFO-логирование успешного удаления

        return ResponseEntity.ok("Урок с id " + id + " успешно удален");
    }

    public ResponseEntity<?> updateLesson(long id, @Valid LessonDto lessonDto) {
        if (id <= 0) {
            log.error("Неправильный ID для обновления: {}", id);
            throw new IllegalArgumentException("Неправильный ID. Он должен быть больше 0.");
        }

        log.info("Ищем урок с id {} для обновления", id);
        Lesson lesson = lessonReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Урок с id " + id + " не найден"));

        LocalDateTime now = LocalDateTime.now();

        // Обновление только переданных полей (если они не null)
        if (lessonDto.getName() != null) {
            lesson.setName(lessonDto.getName());
            log.debug("Обновлено имя урока: {}", lessonDto.getName()); // DEBUG-логирование
        }
        if (lessonDto.getDescription() != null) {
            lesson.setDescription(lessonDto.getDescription());
            log.debug("Обновлено описание урока: {}", lessonDto.getDescription()); // DEBUG-логирование
        }
        if (lessonDto.getContent() != null) {
            lesson.setContent(lessonDto.getContent());
            log.debug("Обновлено содержимое урока"); // DEBUG-логирование
        }
        if (lessonDto.getOrder() != 0) {
            lesson.setOrder(lessonDto.getOrder());
            log.debug("Обновлен порядок урока: {}", lessonDto.getOrder()); // DEBUG-логирование
        }

        // Обновляем время изменения
        lesson.setUpdatedTime(now);

        Lesson updatedLesson = lessonReposiroty.save(lesson);
        LessonDto updatedLessonDto = entityMapping.toDto(updatedLesson);

        log.info("Урок с id {} успешно обновлен", id); // INFO-логирование успешного завершения

        return ResponseEntity.ok(updatedLessonDto);
    }
}
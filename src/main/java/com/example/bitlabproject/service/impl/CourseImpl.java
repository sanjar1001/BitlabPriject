package com.example.bitlabproject.service.impl;

import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.exception.NotFoundException;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.CourseReposiroty;
import com.example.bitlabproject.service.CourseService;
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
public class CourseImpl implements CourseService {

    private final Logger log = LoggerFactory.getLogger(ChapterImpl.class);
    private final CourseReposiroty courseReposiroty;
    private final EntityMapping entityMapping;

    public ResponseEntity<?> getCourseById(long id) throws Exception {

        // Проверка на неправильный id
        if (id <= 0) {
            log.error("Ошибка: Неправильный ID. Он должен быть больше 0.");  // ERROR: Неверный ID
            throw new IllegalArgumentException("Неправильный ID. Он должен быть больше 0.");
        }

        // Поиск курса по id
        Course course = courseReposiroty.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка: Курс с id {} не найден", id);  // ERROR: Если курс не найден
                    return new NotFoundException("Курс с id " + id + " не найден");
                });

        // DEBUG: Логируем информацию о найденном курсе
        log.debug("Найден курс с id: {}. Название курса: {}", id, course.getName());

        // Преобразование в DTO
        CourseDto courseDto = entityMapping.toDto(course);
        return ResponseEntity.ok(courseDto);
    }

    public ResponseEntity<?> createCourse(@Valid CourseDto courseDto) throws Exception {

        // Проверка на пустые данные
        if (courseDto == null) {
            log.error("Ошибка: Данные курса не могут быть пустыми");  // ERROR: Пустые данные курса
            throw new IllegalArgumentException("Данные курса не могут быть пустыми");
        }

        // INFO: Логируем создание нового курса
        log.info("Создание нового курса с названием: {}", courseDto.getName());

        // Создание нового курса
        LocalDateTime now = LocalDateTime.now();
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setCreatedTime(now);

        // Сохранение курса
        Course savedCourse = courseReposiroty.save(course);
        CourseDto savedCourseDto = entityMapping.toDto(savedCourse);

        // DEBUG: Логируем сохранённый курс
        log.debug("Курс успешно создан: {} с id: {}", savedCourseDto.getName(), savedCourseDto.getCourseId());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourseDto);
    }

    public ResponseEntity<?> updateCourse(long id, @Valid CourseDto courseDto) throws Exception {

        // Проверка на неправильный id
        if (id <= 0) {
            log.error("Ошибка: Неправильный ID. Он должен быть больше 0.");  // ERROR: Неверный ID
            throw new IllegalArgumentException("Неправильный ID. Он должен быть больше 0.");
        }

        // Поиск курса по id
        Course course = courseReposiroty.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка: Курс с id {} не найден", id);  // ERROR: Если курс не найден
                    return new NotFoundException("Курс с id " + id + " не найден");
                });

        // INFO: Логируем обновление курса
        log.info("Обновление курса с id: {}", id);

        // Обновление данных курса
        if (courseDto.getName() != null) {
            course.setName(courseDto.getName());
        }
        if (courseDto.getDescription() != null) {
            course.setDescription(courseDto.getDescription());
        }

        LocalDateTime now = LocalDateTime.now();
        course.setUpdatedTime(now);

        // Сохранение обновлённого курса
        Course updatedCourse = courseReposiroty.save(course);
        CourseDto updatedCourseDto = entityMapping.toDto(updatedCourse);

        // DEBUG: Логируем обновлённый курс
        log.debug("Курс обновлён. Новое имя курса: {}. Новое описание: {}", updatedCourseDto.getName(), updatedCourseDto.getDescription());

        return ResponseEntity.ok(updatedCourseDto);
    }

    public ResponseEntity<?> deleteCourse(long id) throws Exception {

        // Поиск курса по id
        Course course = courseReposiroty.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка: Курс с id {} не найден", id);  // ERROR: Если курс не найден
                    return new NotFoundException("Глава не найдена с id: " + id);
                });

        // INFO: Логируем удаление курса
        log.info("Удаление курса с id: {}", id);

        // Удаление курса
        courseReposiroty.deleteById(id);

        // DEBUG: Логируем информацию о удалённом курсе
        log.debug("Курс с id: {} успешно удалён", id);

        return ResponseEntity.ok(course);
    }




}

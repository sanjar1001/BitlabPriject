package com.example.bitlabproject.serviceTest;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.dto.LessonDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.entity.Lesson;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.ChapterRepository;
import com.example.bitlabproject.repository.CourseRepository;
import com.example.bitlabproject.repository.LessonRepository;
import com.example.bitlabproject.service.impl.CourseImpl;
import com.example.bitlabproject.service.impl.LessonImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestLessonService {

    @InjectMocks
    private LessonImpl lessonImpl; // тот самый сервис

    @Mock
    private LessonRepository lessonRepository; // поддельный репозиторий

    @Mock
    private ChapterRepository chapterRepository; // поддельный репозиторий

    @Mock
    private EntityMapping entityMapping; // маппер для перевода между Entity и DTO

    private Lesson lesson;
    private LessonDto lessonDto;
    private LocalDateTime date;
    private Chapter chapter;
    private ChapterDto chapterDto;

    // Созданные объекты для тестирования
    @BeforeEach
    void setUp() {
        date = LocalDateTime.of(2024,07,14,12,0);
        lesson = new Lesson(1L, "Java урок 1", "Урок по установки java", null, 1, null, date, date);
        lessonDto = new LessonDto(1L, "Java урок 1", "Урок по установки java", null, 1, date, date, null);
        chapter = new Chapter(1L, "Java OOP", 1, null, date, date, null);
        chapterDto = new ChapterDto(1L, "Java JVM", 1, 1L, date , date, null);
    }

    // Тест для получения по Id
    @Test
    void testGetLessonById() throws Exception {
        // Подготовка данных
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(entityMapping.toDto(lesson)).thenReturn(lessonDto);

        // Вызов метода
        ResponseEntity<LessonDto> response = lessonImpl.findById(1L);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lessonDto, response.getBody());
    }

    @Test
    void testCreateLesson() {
        // Подготовка данных
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
        when(entityMapping.toDto(lesson)).thenReturn(lessonDto);

        // Вызов метода
        ResponseEntity<LessonDto> response = lessonImpl.createLesson(1L, lessonDto);

        // Проверка
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(lessonDto, response.getBody());

        // Проверка вызовов
        verify(chapterRepository).findById(1L);
        verify(lessonRepository).save(any(Lesson.class));
        verify(entityMapping).toDto(lesson);
    }


        @Test
        void testUpdateCourse() throws Exception {
            // Мокаем поведение репозитория
            when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));  // Курс найден по ID
            when(lessonRepository.save(lesson)).thenReturn(lesson);  // Курс сохраняется
            when(entityMapping.toDto(lesson)).thenReturn(lessonDto);  // Маппер преобразует в DTO

            // Вызов метода
            ResponseEntity<LessonDto> response = lessonImpl.updateLesson(1L, lessonDto);

            // Проверки
            assertEquals(HttpStatus.OK, response.getStatusCode());  // Статус должен быть 200 OK
            assertEquals(lessonDto, response.getBody());  // Ответ должен быть обновленным курсом

            // Проверка, что метод findById был вызван один раз с id 1
            verify(lessonRepository, times(1)).findById(1L);
            // Проверка, что метод save был вызван один раз для сохранения курса
            verify(lessonRepository, times(1)).save(lesson);
            // Проверка, что метод toDto был вызван один раз для преобразования в DTO
            verify(entityMapping, times(1)).toDto(lesson);
        }

        //Тест на удаление курса
        @Test
        void deleteCourse() throws Exception {
            Long findId = 1L;
            when(lessonRepository.findById(lesson.getId())).thenReturn(Optional.of(lesson));

            lessonImpl.deleteLesson(findId);

            verify(lessonRepository, times(1)).findById(findId);
        }




}




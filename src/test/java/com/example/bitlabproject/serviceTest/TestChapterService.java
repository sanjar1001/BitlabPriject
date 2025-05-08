package com.example.bitlabproject.serviceTest;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.ChapterRepository;
import com.example.bitlabproject.repository.CourseRepository;
import com.example.bitlabproject.service.impl.ChapterImpl;
import com.example.bitlabproject.service.impl.CourseImpl;
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
  public class TestChapterService {

        @InjectMocks
        private ChapterImpl chapterImpl; // тот самый сервис

        @Mock
        private ChapterRepository chapterRepository; // поддельный репозиторий

        @Mock
        private CourseRepository courseRepository; // поддельный репозиторий

        @Mock
        private EntityMapping entityMapping; // маппер для перевода между Entity и DTO

        private Chapter chapter;
        private ChapterDto chapterDto;
        private Course course;
        private LocalDateTime date;

    // Созданные объекты для тестирования
    @BeforeEach
    void setUp() {
        date = LocalDateTime.of(2024,07,14,12,0);
        course = new Course(1L,"Java","1J",date,date,null);
        chapter = new Chapter(1L, "Java OOP", 1, null, date, date, null);
        chapterDto = new ChapterDto(1L, "Java OOP", 1, 1L, date , date, null);
    }

        // Тест для получения главы по Id
        @Test
        void testGetChapterById() throws Exception {
            // Подготовка данных
            when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
            when(entityMapping.toDto(chapter)).thenReturn(chapterDto);

            // Вызов метода
            ResponseEntity<ChapterDto> response = chapterImpl.getChapterById(1L);

            // Проверка результата
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(chapterDto, response.getBody());
        }


    // Для создания курса
    @Test
    void testCreateCourse() throws Exception {

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(chapterRepository.save(any(Chapter.class))).thenReturn(chapter);

        when(entityMapping.toDto(chapter)).thenReturn(chapterDto);

        ResponseEntity<ChapterDto> response = chapterImpl.createChapter(1L, chapterDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(chapterDto, response.getBody());

    }



    }




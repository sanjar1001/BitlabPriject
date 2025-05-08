package com.example.bitlabproject.serviceTest;

import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.CourseRepository;
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
  public class TestCourseService {

        @InjectMocks
        private CourseImpl courseImpl; // тот самый сервис

        @Mock
        private CourseRepository courseRepository; // поддельный репозиторий

        @Mock
        private EntityMapping entityMapping; // маппер для перевода между Entity и DTO

        private Course course;
        private CourseDto courseDto;
        private LocalDateTime date;

        // Созданные объекты для тестирования
        @BeforeEach
        void setUp() {
            date = LocalDateTime.of(2024,07,14,12,0);
            course = new Course(1L,"Java","1J",date,date,null);
            courseDto = new CourseDto(1L,"Java","1J",date,date,null);
        }

        // Тест для получения по Id
        @Test
        void testGetCourseById() throws Exception {
            // Подготовка данных
            when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
            when(entityMapping.toDto(course)).thenReturn(courseDto);

            // Вызов метода
            ResponseEntity<CourseDto> response = courseImpl.getCourseById(1L);

            // Проверка результата
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(courseDto, response.getBody());
        }

        // Для создания курса
        @Test
        void testCreateCourse() throws Exception {

            when(courseRepository.save(any(Course.class))).thenReturn(course);

            when(entityMapping.toDto(course)).thenReturn(courseDto);

            ResponseEntity<CourseDto> response = courseImpl.createCourse(courseDto)
                    ;
            assertEquals(HttpStatus.CREATED, response.getStatusCode());

            assertEquals(courseDto, response.getBody());

        }

        @Test
        void testUpdateCourse() throws Exception {
            // Мокаем поведение репозитория
            when(courseRepository.findById(1L)).thenReturn(Optional.of(course));  // Курс найден по ID
            when(courseRepository.save(course)).thenReturn(course);  // Курс сохраняется
            when(entityMapping.toDto(course)).thenReturn(courseDto);  // Маппер преобразует в DTO

            // Вызов метода
            ResponseEntity<CourseDto> response = courseImpl.updateCourse(1L, courseDto);

            // Проверки
            assertEquals(HttpStatus.OK, response.getStatusCode());  // Статус должен быть 200 OK
            assertEquals(courseDto, response.getBody());  // Ответ должен быть обновленным курсом

            // Проверка, что метод findById был вызван один раз с id 1
            verify(courseRepository, times(1)).findById(1L);
            // Проверка, что метод save был вызван один раз для сохранения курса
            verify(courseRepository, times(1)).save(course);
            // Проверка, что метод toDto был вызван один раз для преобразования в DTO
            verify(entityMapping, times(1)).toDto(course);
        }

        //Тест на удаление курса
        @Test
        void deleteCourse() throws Exception {
            Long findId = 1L;
            when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

            courseImpl.deleteCourse(findId);

            verify(courseRepository, times(1)).findById(findId);
        }




    }




package com.example.bitlabproject.controllerTest;

import com.example.bitlabproject.controller.CourseController;
import com.example.bitlabproject.controller.LessonController;
import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.dto.LessonDto;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.entity.Lesson;
import com.example.bitlabproject.service.CourseService;
import com.example.bitlabproject.service.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LessonController.class)
public class LessonControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LessonService lessonService;

    private Lesson lesson;
    private LessonDto lessonDto;
    private LocalDateTime date;


    // Созданные объекты для тестирования
    @BeforeEach
    void setUp() {
        date = LocalDateTime.of(2024,07,14,12,0);
        lesson = new Lesson(1L, "Java урок 1", "Урок по установки java", null, 1, null, date, date);
        lessonDto = new LessonDto(1L, "Java урок 1", "Урок по установки java", null, 1, date, date, null);
    }

    //Получение главы по Id
    @Test
    void testGetLessonById() throws Exception {
        // Мокаем успешное получение урока по ID
        when(lessonService.findById(1L))
                .thenReturn(ResponseEntity.ok(lessonDto));  // Возвращаем урок с данным ID

        // Выполняем запрос на получение урока по ID
        mockMvc.perform(get("/lesson/get/1"))  // Убедись, что путь совпадает с аннотацией
                .andExpect(status().isOk())  // Проверяем статус 200 OK
                .andExpect(jsonPath("$.id").value(1L))  // Проверяем, что в JSON есть id = 1
                .andExpect(jsonPath("$.name").value("Java урок 1"));  // И имя урока "Java урок 1"
    }


    //Создание главы по параметрам
    @Test
    void testCreateLesson() throws Exception {
        // Мокаем успешное создание урока
        when(lessonService.createLesson(1L, lessonDto))
                .thenReturn(ResponseEntity.ok().build());

        // Создаем ObjectMapper для сериализации
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Для обработки LocalDateTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Отключаем timestamp

        // Выполняем запрос на создание урока
        mockMvc.perform(post("/lesson/create/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonDto)))  // Сериализуем lessonDto в JSON
                .andExpect(status().isOk());  // Проверяем статус ответа 200 OK
    }

    @Test
    void testUpdateLesson() throws Exception {
        // Мокаем успешное обновление
        when(lessonService.updateLesson(eq(1L), any(LessonDto.class)))
                .thenReturn(ResponseEntity.ok(lessonDto));  // Возвращаем обновленный урок

        // Создаем ObjectMapper для сериализации
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Для обработки LocalDateTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Отключаем timestamp

        // Выполняем запрос на обновление
        mockMvc.perform(patch("/lesson/update/1")  // Убедись, что путь совпадает с аннотацией
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonDto)))  // Сериализуем lessonDto в JSON
                .andExpect(status().isOk())  // Проверяем, что ответ с кодом 200 OK
                .andExpect(content().json(objectMapper.writeValueAsString(lessonDto)));  // Проверяем, что возвращенный объект соответствует lessonDto
    }


    @Test
    void testDeleteLesson() throws Exception {
        // Мокаем успешное удаление урока
        when(lessonService.deleteLesson(1L))
                .thenReturn(ResponseEntity.ok().build());

        // Выполняем запрос на удаление
        mockMvc.perform(delete("/lesson/delete/1"))
                .andExpect(status().isOk());  // Проверяем, что статус ответа 200 OK
    }







}

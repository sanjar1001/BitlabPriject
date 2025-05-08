package com.example.bitlabproject.controllerTest;

import com.example.bitlabproject.controller.ChapterController;
import com.example.bitlabproject.controller.CourseController;
import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.service.ChapterService;
import com.example.bitlabproject.service.CourseService;
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

@WebMvcTest(controllers = CourseController.class)
public class CourseControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseService courseService;

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

    //Получение главы по Id
    @Test
    void testGetCourseById() throws Exception {

        when(courseService.getCourseById(1L))
                .thenReturn(ResponseEntity.ok(courseDto));


        mockMvc.perform(get("/course/get/1"))
                .andExpect(status().isOk()) // Ожидаем статус 200 OK
                .andExpect(jsonPath("$.courseId").value(1L)) // Проверяем, что в JSON есть id = 1
                .andExpect(jsonPath("$.name").value("Java")); // И имя Java OOP
    }

    //Создание главы по параметрам
    @Test
    void testCreateCourse() throws Exception {
        // Мокаем успешное создание курса
        when(courseService.createCourse(any(CourseDto.class)))
                .thenReturn(ResponseEntity.ok().build());

        // Создаем ObjectMapper для сериализации
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Для обработки LocalDateTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Отключаем timestamp

        // Выполняем запрос
        mockMvc.perform(post("/course/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDto)))  // Сериализуем courseDto в JSON
                .andExpect(status().isOk());  // Проверяем статус ответа 200 OK
    }

    //Тест для обновления данных
    @Test
    void testUpdateCourse() throws Exception {
        // Мокаем успешное обновление
        when(courseService.updateCourse(eq(1L), any(CourseDto.class)))
                .thenReturn(ResponseEntity.ok(courseDto));  // Возвращаем обновленный курс

        // Создаем ObjectMapper для сериализации
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Для обработки LocalDateTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Отключаем timestamp

        // Выполняем запрос на обновление
        mockMvc.perform(patch("/course/update/1")  // Убедись, что путь совпадает с аннотацией
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDto)))  // Сериализуем курс в JSON
                .andExpect(status().isOk())  // Проверяем, что ответ с кодом 200 OK
                .andExpect(content().json(objectMapper.writeValueAsString(courseDto)));  // Проверяем, что возвращенный объект соответствует courseDto
    }


    @Test
    void testDeleteCourse() throws Exception {
        // Мокаем успешное удаление курса
        when(courseService.deleteCourse(1L))
                .thenReturn(ResponseEntity.ok().build());

        // Выполняем запрос на удаление
        mockMvc.perform(delete("/course/delete/1"))
                .andExpect(status().isOk());  // Проверяем, что статус ответа 200 OK
    }






}

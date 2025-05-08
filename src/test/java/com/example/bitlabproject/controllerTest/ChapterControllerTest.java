package com.example.bitlabproject.controllerTest;

import com.example.bitlabproject.controller.ChapterController;
import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.service.ChapterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.mockito.Mockito.when;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ChapterController.class)
public class ChapterControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChapterService chapterService;

    private Chapter chapter;
    private ChapterDto chapterDto;
    private LocalDateTime date;
    private Course course;


    // Созданные объекты для тестирования
    @BeforeEach
    void setUp() {
        date = LocalDateTime.of(2024,07,14,12,0);
        course = new Course(1L,"Java","1J",date,date,null);
        chapter = new Chapter(1L, "Java OOP", 1, course, date, date, null);
        chapterDto = new ChapterDto(1L, "Java JVM", 1, 1L, date , date, null);
    }

    //Получение главы по Id
    @Test
    void testGetChapterById() throws Exception {

        when(chapterService.getChapterById(1L))
                .thenReturn(ResponseEntity.ok(chapterDto));


        mockMvc.perform(get("/chapter/get/1"))
                .andExpect(status().isOk()) // Ожидаем статус 200 OK
                .andExpect(jsonPath("$.id").value(1L)) // Проверяем, что в JSON есть id = 1
                .andExpect(jsonPath("$.name").value("Java JVM")); // И имя Java OOP
    }

    //Создание главы по параметрам
    @Test
    void testCreateChapter() throws Exception {
        when(chapterService.createChapter(eq(5L), any(ChapterDto.class)))
                .thenReturn(ResponseEntity.ok().build());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(post("/chapter/create/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chapterDto)))
                .andExpect(status().isOk());
    }

    //Тест для обновление данных
    @Test
    void testUpdateChapter() throws Exception {
        // Мокаем успешное обновление
        when(chapterService.updateChapter(eq(5L), any(ChapterDto.class)))
                .thenReturn(ResponseEntity.ok(chapterDto)); // Возвращаем обновленный DTO

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Чтобы правильно обработать LocalDateTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Чтобы избежать timestamp

        // Выполняем запрос
        mockMvc.perform(patch("/chapter/update/5") // Убедись, что путь совпадает с аннотацией
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chapterDto)))
                .andExpect(status().isOk()) // Проверяем, что ответ с кодом 200 OK
                .andExpect(content().json(objectMapper.writeValueAsString(chapterDto))); // Проверяем, что возвращенный объект соответствует chapterDto
    }



    @Test
    void testDeleteChapter() throws Exception {
        when(chapterService.deleteChapter(1L))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/chapter/delete/1"))
                .andExpect(status().isOk());
    }





}

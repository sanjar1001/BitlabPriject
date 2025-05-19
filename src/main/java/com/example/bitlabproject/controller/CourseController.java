package com.example.bitlabproject.controller;

import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.CourseRepository;
import com.example.bitlabproject.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/course")
@RequiredArgsConstructor
@Tag(name = "Курсы", description = "API для получение курса")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Получить список курсов", description = "Возвращает список всех курсов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список курсов успешно получен")
    })
    @GetMapping("/course/get/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public ResponseEntity<List<CourseDto>> getCourseList() throws Exception {
        return courseService.getAllCourses();
    }

    @Operation(summary = "Получить курс по ID", description = "Возвращает информацию о курсе по его идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Курс найден"),
            @ApiResponse(responseCode = "404", description = "Курс не найден"),
            @ApiResponse(responseCode = "400", description = "Неверный ID")
    })
    @GetMapping("/course/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public ResponseEntity<CourseDto> getCourse(@PathVariable long id) throws Exception {

        return courseService.getCourseById(id);
    }

    @Operation(summary = "Создать новый курс", description = "Создаёт новый курс на основе переданных данных.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Курс успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    @PostMapping("/course/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?>  addCourse(@RequestBody CourseDto courseDto) throws Exception {

        return courseService.createCourse(courseDto);

    }

    @Operation(summary = "Обновить курс", description = "Обновляет существующий курс по ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Курс успешно обновлён"),
            @ApiResponse(responseCode = "404", description = "Курс не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    })
    @PatchMapping("/course/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?>  updateCourse(@PathVariable long id, @RequestBody CourseDto courseDto) throws Exception {
        return courseService.updateCourse(id,courseDto);
    }

    @Operation(summary = "Обновить курс", description = "Обновляет существующий курс по ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Курс успешно обновлён"),
            @ApiResponse(responseCode = "404", description = "Курс не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    })
    @DeleteMapping("/course/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?>  deleteCourse(@PathVariable long id) throws Exception {
        return courseService.deleteCourse(id);
    }

}

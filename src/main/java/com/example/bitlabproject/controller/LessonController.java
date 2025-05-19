package com.example.bitlabproject.controller;

import com.example.bitlabproject.dto.LessonDto;
import com.example.bitlabproject.service.LessonService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("/lesson")
@RequiredArgsConstructor
@Tag(name = "LessonController", description = "API для получение урока")
public class LessonController {

    private final LessonService lessonService;

    @Operation(summary = "Создать урок", description = "Создание нового урока для определённой главы по ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Урок успешно создан"),
            @ApiResponse(responseCode = "404", description = "Глава не найдена"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    @PostMapping("/lesson/create/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<LessonDto> createLesson(@PathVariable long id, @RequestBody LessonDto lessonDto) throws Exception {

        return lessonService.createLesson(id, lessonDto);

    } //Создание Урока для главы

    @Operation(summary = "Получить урок", description = "Получить информацию об уроке по его ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Урок найден"),
            @ApiResponse(responseCode = "404", description = "Урок не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный ID")
    })
    @GetMapping("/lesson/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public ResponseEntity<?> getLesson(@PathVariable long id) throws Exception {

        return lessonService.findById(id);

    } //Получить Урок по ID

    @Operation(summary = "Удалить урок", description = "Удалить урок по его ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Урок успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Урок не найден"),
            @ApiResponse(responseCode = "400", description = "Неверный ID")
    })
    @DeleteMapping("/lesson/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteLesson(@PathVariable long id) throws Exception {
        return lessonService.deleteLesson(id);
    } //Удалить урок по ID

    @Operation(summary = "Обновить урок", description = "Обновить существующий урок по его ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Урок успешно обновлён"),
            @ApiResponse(responseCode = "404", description = "Урок не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    })
    @PatchMapping("/lesson/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateCourse(@PathVariable long id, @RequestBody LessonDto lessonDto) throws Exception {
        return lessonService.updateLesson(id,lessonDto);
    } //Изменить урок по ID



}

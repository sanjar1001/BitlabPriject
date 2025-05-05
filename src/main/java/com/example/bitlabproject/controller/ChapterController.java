package com.example.bitlabproject.controller;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.service.ChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController("/chapter")
@RequiredArgsConstructor
@Tag(name = "ChapterController", description = "API для получение главы")
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping("/chapter/get/{id}")
    @Operation(summary = "Получить главу по ID", description = "Возвращает информацию о главы по его идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Глава найден"),
            @ApiResponse(responseCode = "404", description = "Глава не найден"),
            @ApiResponse(responseCode = "400", description = "Неверный ID")
    })
    public ResponseEntity<?> getChapter(@PathVariable long id) throws Exception {
        return chapterService.getChapterById(id);
    }

    @Operation(summary = "Создать главу", description = "Создание новой главы для курса по ID курса.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Глава успешно создана"),
            @ApiResponse(responseCode = "400", description = "Неверный ID или некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Курс с таким ID не найден")
    })
    @PostMapping("/chapter/create/{id}")
    public ResponseEntity<?> createChapter(@PathVariable long id, @RequestBody ChapterDto chapterDto) throws Exception {
        return chapterService.createChapter(id, chapterDto);
    }//Создание новой главы для курса

    @Operation(summary = "Обновить главу", description = "Обновление существующей главы по её ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Глава успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "404", description = "Глава с таким ID не найдена")
    })
    @PatchMapping("/chapter/update/{id}")
    public ResponseEntity<?> updateChapter(@PathVariable long id, @RequestBody ChapterDto chapterDto) throws Exception {
        return chapterService.updateChapter(id, chapterDto);
    }

    @Operation(summary = "Удалить главу", description = "Удаление главы по её ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Глава успешно удалена"),
            @ApiResponse(responseCode = "400", description = "Неверный ID"),
            @ApiResponse(responseCode = "404", description = "Глава с таким ID не найдена")
    })
    @DeleteMapping("/chapter/delete/{id}")
    public ResponseEntity<?> deleteChapter(@PathVariable long id) throws Exception {
        return chapterService.deleteChapter(id);
    }



}



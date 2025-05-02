package com.example.bitlabproject.controller;

import com.example.bitlabproject.dto.LessonDto;
import com.example.bitlabproject.repository.LessonReposiroty;
import com.example.bitlabproject.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonReposiroty lessonReposiroty;
    private final LessonService lessonService;

    @PostMapping("/lesson/create/{id}")
    public ResponseEntity<?> createLesson(@PathVariable long id, @RequestBody LessonDto lessonDto) throws Exception {

        return lessonService.createLesson(id, lessonDto);

    } //Создание Урока для главы

    @GetMapping("/lesson/get/{id}")
    public ResponseEntity<?> getLesson(@PathVariable long id) throws Exception {

        return lessonService.findById(id);

    } //Получить Урок по ID

    @DeleteMapping("/lesson/delete/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable long id) throws Exception {
        return lessonService.deleteLesson(id);
    } //Удалить урок по ID

    @PatchMapping("/lesson/update/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable long id, @RequestBody LessonDto lessonDto) throws Exception {
        return lessonService.updateLesson(id,lessonDto);
    } //Изменить урок по ID



}

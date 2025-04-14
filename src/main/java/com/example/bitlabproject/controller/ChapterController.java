package com.example.bitlabproject.controller;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.CourseReposiroty;
import com.example.bitlabproject.service.ChapterService;
import com.example.bitlabproject.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/chapter")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping("/chapter/get/{id}")
    public ResponseEntity<?> getChapter(@PathVariable long id) throws Exception {
        return chapterService.getChapterById(id);
    }

    @PostMapping("/chapter/create/{id}")
    public Chapter createChapter(@PathVariable long id, @RequestBody ChapterDto chapterDto) throws Exception {
        return chapterService.createChapter(id, chapterDto);
    }//Создание новой главы для курса

    @PatchMapping("/chapter/update/{id}")
    public Chapter updateChapter(@PathVariable long id, @RequestBody ChapterDto chapterDto) throws Exception {
        return chapterService.updateChapter(id, chapterDto);
    }

    @DeleteMapping("/chapter/delete/{id}")
    public void deleteChapter(@PathVariable long id) throws Exception {
        chapterService.deleteChapter(id);
    }



}

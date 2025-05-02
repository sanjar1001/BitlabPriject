package com.example.bitlabproject.controller;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/chapter")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping("/chapter/get/{id}")
    public ResponseEntity<?> getChapter(@PathVariable long id) throws Exception {
        return chapterService.getChapterById(id);
    }

    @PostMapping("/chapter/create/{id}")
    public ResponseEntity<?> createChapter(@PathVariable long id, @RequestBody ChapterDto chapterDto) throws Exception {
        return chapterService.createChapter(id, chapterDto);
    }//Создание новой главы для курса

    @PatchMapping("/chapter/update/{id}")
    public ResponseEntity<?> updateChapter(@PathVariable long id, @RequestBody ChapterDto chapterDto) throws Exception {
        return chapterService.updateChapter(id, chapterDto);
    }

    @DeleteMapping("/chapter/delete/{id}")
    public ResponseEntity<?> deleteChapter(@PathVariable long id) throws Exception {
        return chapterService.deleteChapter(id);
    }



}

package com.example.bitlabproject.service.impl;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.exception.NotFoundException;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.ChapterReposiroty;
import com.example.bitlabproject.repository.CourseReposiroty;
import com.example.bitlabproject.service.ChapterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChapterImpl implements ChapterService {

    private final ChapterReposiroty chapterReposiroty;
    private final CourseReposiroty courseReposiroty;
    private final EntityMapping entityMapping;

    public ResponseEntity<?> createChapter(long id, @Valid ChapterDto chapterDto) throws Exception {

        if (id <= 0) {
            throw new IllegalArgumentException("Id не должен быть ниже 0");
        }

        Course course = courseReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс не найден: " + id));

        LocalDateTime now = LocalDateTime.now();

        Chapter chapter = new Chapter();
        chapter.setName(chapterDto.getName());
        chapter.setOrder(chapterDto.getOrder());
        chapter.setCourse(course);
        chapter.setCreatedTime(now);
        chapterReposiroty.save(chapter);

        return ResponseEntity.ok(chapter);
    }

    public ResponseEntity<?> updateChapter(long id, ChapterDto chapterDto) throws Exception {

        if (id <= 0) {
            throw new IllegalArgumentException("Id не должен быть ниже 0");
        }

        Chapter chapter = chapterReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Глава не найдена с id: " + id));

        LocalDateTime now = LocalDateTime.now();

        if (chapterDto.getName() != null) {
            chapter.setName(chapterDto.getName());
        }
        if (chapterDto.getOrder() != 0) {
            chapter.setOrder(chapterDto.getOrder());
        }
        chapter.setUpdatedTime(now);
        chapterReposiroty.save(chapter);

        return ResponseEntity.ok(chapter);
    }

    public ResponseEntity<?> deleteChapter(long id) throws Exception {

        Chapter chapter = chapterReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Глава не найдена с id: " + id));

        chapterReposiroty.deleteById(id);

        return ResponseEntity.ok(chapter);
    }

    public ResponseEntity<?> getChapterById(long id) throws Exception {

        if (id <= 0) {
            throw new IllegalArgumentException("Неправильный ввод данных");
        }

        Chapter chapter = chapterReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Глава не найдена с id: " + id));

        ChapterDto chapterDto = entityMapping.toDto(chapter);

        return ResponseEntity.ok(chapterDto);
    }


}

package com.example.bitlabproject.service.impl;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.exception.NotFoundException;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.ChapterReposiroty;
import com.example.bitlabproject.repository.CourseReposiroty;
import com.example.bitlabproject.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChapterImpl implements ChapterService {

    private final ChapterReposiroty chapterReposiroty;
    private final CourseReposiroty courseReposiroty;
    private final EntityMapping entityMapping;

    public Chapter createChapter(long id, ChapterDto chapterDto) throws Exception {
        Course course = courseReposiroty.findById(id)
                .orElseThrow(() -> new NullPointerException("Course with id " + id + " not found"));

        LocalDateTime now = LocalDateTime.now();


            Chapter chapter = new Chapter();
            chapter.setName(chapterDto.getName());
            chapter.setOrder(chapterDto.getOrder());
            chapter.setCourse(course);
            chapter.setCreatedTime(now);
            return chapterReposiroty.save(chapter);


    }// Создание Главы для курса.

    @Override
    public Chapter updateChapter(long id, ChapterDto chapterDto) throws Exception {

        Chapter chapter = chapterReposiroty.findById(id)
                .orElseThrow(() -> new NullPointerException("Глава который вы ищите " + id + " нету!"));

        LocalDateTime now = LocalDateTime.now();

        if (chapterDto.getName() != null) {
            chapter.setName(chapterDto.getName());
        }
        if (chapterDto.getOrder() != 0){
            chapter.setOrder(chapterDto.getOrder());
        }
        chapter.setUpdatedTime(now);
        return chapterReposiroty.save(chapter);

    } // Обновить значение Главы

    @Override
    public void deleteChapter(long id) throws Exception {
        Chapter chapter = chapterReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Глава который вы хотите удалить " + id + " нету!"));

        chapterReposiroty.deleteById(id);
    } // Удаление Главы по ID

    @Override
    public ChapterDto getChapterById(long id) throws Exception {

        Chapter chapter = chapterReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Глава который ищите нету!"));

        return entityMapping.toDto(chapter);

    }


}

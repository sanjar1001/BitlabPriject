package com.example.bitlabproject.service.impl;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.dto.LessonDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.entity.Lesson;
import com.example.bitlabproject.exception.NotFoundException;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.ChapterReposiroty;
import com.example.bitlabproject.repository.LessonReposiroty;
import com.example.bitlabproject.service.LessonService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LessonImpl implements LessonService {

    private final LessonReposiroty lessonReposiroty;
    private final ChapterReposiroty chapterReposiroty;
    private final EntityMapping entityMapping;



    public LessonDto createLesson(long id, LessonDto lessonDto) {

        System.out.println(id);


        Chapter chapter = chapterReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Такой главы не существует"));

        LocalDateTime now = LocalDateTime.now();
        Lesson lesson = new Lesson();
        lesson.setName(lessonDto.getName());
        lesson.setDescription(lessonDto.getDescription());
        lesson.setContent(lessonDto.getContent());
        lesson.setOrder(lessonDto.getOrder());
        lesson.setCreatedTime(now);
        lesson.setChapter(chapter);
        lessonReposiroty.save(lesson);

        return entityMapping.toDto(lesson);
    } // Создание Урока для главы

    public LessonDto findById(long id) {

        Lesson lesson =lessonReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Нету такой главы"));

        return entityMapping.toDto(lesson);
    } //Найти Урока по Id

    public void deleteLesson(long id) {
        Lesson lesson = lessonReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс который вы хотите удалить " + id + " нету!"));

        lessonReposiroty.deleteById(id);
    } // Удаление Урока по ID

    @Override
    public LessonDto updateLesson(long id, LessonDto lessonDto) {
        Lesson lesson = lessonReposiroty.findById(id)
                .orElseThrow(() -> new NotFoundException("Урок с id " + id + " не найден"));

        LocalDateTime now = LocalDateTime.now();

        // Обновление только переданных полей (если они не null)
        if (lessonDto.getName() != null) {
            lesson.setName(lessonDto.getName());
        }
        if (lessonDto.getDescription() != null) {
            lesson.setDescription(lessonDto.getDescription());
        }
        if (lessonDto.getContent() != null) {
            lesson.setContent(lessonDto.getContent());
        }
        if (lessonDto.getOrder() != 0) {  // Здесь ты можешь добавить проверку на значение
            lesson.setOrder(lessonDto.getOrder());
        }

        // Обновляем время изменения
        lesson.setUpdatedTime(now);

        // Сохраняем изменения
        return entityMapping.toDto(lessonReposiroty.save(lesson));
    }



}

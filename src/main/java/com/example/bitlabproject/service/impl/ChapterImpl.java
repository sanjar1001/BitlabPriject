package com.example.bitlabproject.service.impl;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.exception.NotFoundException;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.ChapterRepository;
import com.example.bitlabproject.repository.CourseRepository;
import com.example.bitlabproject.service.ChapterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChapterImpl implements ChapterService {

    private final Logger log = LoggerFactory.getLogger(ChapterImpl.class);
    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;
    private final EntityMapping entityMapping;

    public ResponseEntity<ChapterDto> createChapter(long id, @Valid ChapterDto chapterDto) throws Exception {
        log.info("Создается новая глава с именем: {}", chapterDto.getName());

        // Проверка на id
        if (id <= 0) {
            log.error("Некорректный id для курса: {}", id);  // Логируем ошибку до выброса исключения
            throw new IllegalArgumentException("Id не должен быть ниже 0");
        }

        // Поиск курса по id
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Курс с id {} не найден!", id);  // Логируем ошибку, если курс не найден
                    return new NotFoundException("Курс не найден: " + id);
                });

        // Создание новой главы
        LocalDateTime now = LocalDateTime.now();
        Chapter chapter = new Chapter();
        chapter.setName(chapterDto.getName());
        chapter.setOrder(chapterDto.getOrder());
        chapter.setCourse(course);
        chapter.setCreatedTime(now);

        // Сохраняем главу в базе
        chapterRepository.save(chapter);
        log.debug("Создана новая глава с именем: {} для курса: {}", chapterDto.getName(), chapterDto.getCourseId());  // Здесь выводим подробности созданной главы

        return ResponseEntity.status(HttpStatus.CREATED).body(chapterDto);
    } // Создание новой главы для курса

    public ResponseEntity<ChapterDto> updateChapter(long id, ChapterDto chapterDto) throws Exception {

        if (id <= 0) {
            log.error("Ошибка: Неверный id для обновления главы: {}", id);
            throw new IllegalArgumentException("Id не должен быть ниже 0");
        }

        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Глава с id {} не найдена", id);
                    return new NotFoundException("Глава не найдена с id: " + id);
                });

        LocalDateTime now = LocalDateTime.now();

        // DEBUG: Выводим только важные данные для отладки
        log.debug("Обновление главы с id: {}. Новые данные: имя = {}, порядок = {}", id, chapterDto.getName(), chapterDto.getOrder());

        if (chapterDto.getName() != null) {
            chapter.setName(chapterDto.getName());
        }
        if (chapterDto.getOrder() != 0) {
            chapter.setOrder(chapterDto.getOrder());
        }
        chapter.setUpdatedTime(now);
        chapterRepository.save(chapter);

        log.info("Глава с id: {} успешно обновлена", id);

        return ResponseEntity.ok(chapterDto);
    } // Обновление главы для курса

    public ResponseEntity<?> deleteChapter(long id) throws Exception {

        // Проверка на наличие главы с данным id
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка: Глава с id {} не найдена", id);
                    return new NotFoundException("Глава не найдена с id: " + id);
                });

        // DEBUG: Логируем информацию о том, что глава будет удалена
        log.debug("Удаление главы с id: {}. Название: {}", id, chapter.getName());

        // Удаление главы
        chapterRepository.deleteById(id);

        // INFO: Логируем успешное удаление
        log.info("Глава с id: {} успешно удалена", id);

        return ResponseEntity.ok(chapter);
    } // Удаление главы

    public ResponseEntity<ChapterDto> getChapterById(long id) throws Exception {

        if (id <= 0) {
            log.error("Ошибка: Неправильный ввод данных id {}", id);
            throw new IllegalArgumentException("Неправильный ввод данных");
        }

        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка: Глава с id {} не найдена", id);
                    return new NotFoundException("Глава не найдена с id: " + id);
                });


        ChapterDto chapterDto = entityMapping.toDto(chapter);

        log.info("Глава с id: {} успешно найдена", id);

        return ResponseEntity.ok(chapterDto);
    }


}

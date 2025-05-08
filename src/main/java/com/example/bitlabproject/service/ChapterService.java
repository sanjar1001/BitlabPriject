package com.example.bitlabproject.service;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.entity.Chapter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ChapterService  {

    ResponseEntity<ChapterDto> createChapter(long id, ChapterDto chapterDto) throws Exception;

    ResponseEntity<ChapterDto> updateChapter(long id, ChapterDto chapterDto) throws Exception;

    ResponseEntity<?> deleteChapter(long id) throws Exception;

    ResponseEntity<ChapterDto> getChapterById(long id) throws Exception;

}

package com.example.bitlabproject.service;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.entity.Chapter;
import org.springframework.stereotype.Service;

@Service
public interface ChapterService  {

    Chapter createChapter(long id, ChapterDto chapterDto) throws Exception;

    Chapter updateChapter(long id, ChapterDto chapterDto) throws Exception;

    void deleteChapter(long id) throws Exception;

    ChapterDto getChapterById(long id) throws Exception;

}

package com.example.bitlabproject.service;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ChapterService  {

    Chapter createChapter(long id, ChapterDto chapterDto) throws Exception;

}

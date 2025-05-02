package com.example.bitlabproject.mapping;

import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.dto.LessonDto;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.entity.Lesson;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface EntityMapping {

    ChapterDto toDto(Chapter chapter);
    Chapter toEntity(ChapterDto chapterDto);

    CourseDto toDto(Course course);
    Course toEntity(CourseDto courseDto);

    LessonDto toDto(Lesson lesson);
    Lesson toEntity(LessonDto lessonDto);

}

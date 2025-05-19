package com.example.bitlabproject.mapping;

import com.example.bitlabproject.dto.AttachmentDto;
import com.example.bitlabproject.dto.ChapterDto;
import com.example.bitlabproject.dto.CourseDto;
import com.example.bitlabproject.dto.LessonDto;
import com.example.bitlabproject.entity.Attachment;
import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface EntityMapping {

        @Mapping(source = "course.id", target = "courseId")
        ChapterDto toDto(Chapter chapter);

        @Mapping(target = "course.id", source = "courseId")
        @Mapping(target = "id", ignore = true)
        Chapter toEntity(ChapterDto chapterDto);

        @Mapping(source = "course.id", target = "courseId")
        CourseDto toDto(Course course);

        @Mapping(target = "id", ignore = true)
        Course toEntity(CourseDto courseDto);

        @Mapping(source = "chapter.id", target = "chapterId")
        LessonDto toDto(Lesson lesson);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "chapter.id", source = "chapterId")
        Lesson toEntity(LessonDto lessonDto);

        @Mapping(source = "lesson.id", target = "lessonId")
        AttachmentDto toDto(Attachment attachment);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "lesson.id", source = "lessonId")
        Attachment toEntity(AttachmentDto attachmentDto);

        List<AttachmentDto> toDto(List<Attachment> attachments);


}

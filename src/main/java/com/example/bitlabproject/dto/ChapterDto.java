package com.example.bitlabproject.dto;

import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.entity.Lesson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChapterDto {

    private String name;
    private int order;
    private Course course;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private List<Lesson> lessons;

}

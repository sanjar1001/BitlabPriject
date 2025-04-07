package com.example.bitlabproject.dto;

import com.example.bitlabproject.entity.Chapter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {

    private String name;
    private String description;
    private String content;
    private int order;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Chapter chapterId;



}

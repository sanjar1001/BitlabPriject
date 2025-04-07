package com.example.bitlabproject.dto;

import com.example.bitlabproject.entity.Chapter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private String name;
    private String description;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private List<Chapter> chapters;


}

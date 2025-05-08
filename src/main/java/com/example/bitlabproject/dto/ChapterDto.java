package com.example.bitlabproject.dto;

import com.example.bitlabproject.entity.Course;
import com.example.bitlabproject.entity.Lesson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    private long id;
    @NotBlank(message = "имя не должно быть пустым")
    private String name;
    @Min(value = 1, message = "Порядок не должен ниже 1")
    private int order;
    private long courseId;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private List<Lesson> lessons;

}

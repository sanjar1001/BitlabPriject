package com.example.bitlabproject.dto;


import com.example.bitlabproject.entity.Lesson;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentDto {

    private Long id;
    private String name;
    private String url;
    private Long lessonId;
    private LocalDateTime createdTime;

}


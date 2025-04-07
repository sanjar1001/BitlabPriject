package com.example.bitlabproject.repository;

import com.example.bitlabproject.entity.Chapter;
import com.example.bitlabproject.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterReposiroty extends JpaRepository<Chapter, Long> {

}

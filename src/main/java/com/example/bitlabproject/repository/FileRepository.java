package com.example.bitlabproject.repository;

import com.example.bitlabproject.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<Attachment, Long> {
}

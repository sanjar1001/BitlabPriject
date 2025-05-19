package com.example.bitlabproject.service.impl;

import com.example.bitlabproject.config.MinioConfig;
import com.example.bitlabproject.dto.AttachmentDto;
import com.example.bitlabproject.entity.Attachment;
import com.example.bitlabproject.entity.Lesson;
import com.example.bitlabproject.exception.FileDownloadException;
import com.example.bitlabproject.exception.NotFoundException;
import com.example.bitlabproject.mapping.EntityMapping;
import com.example.bitlabproject.repository.FileRepository;
import com.example.bitlabproject.repository.LessonRepository;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;
    private final FileRepository fileRepository;
    private final EntityMapping entityMapping;
    private final LessonRepository lessonRepository;

    @Value("${minio.bucket-name:dev-bucket}")
    private String bucket;
    public AttachmentDto uploadFile(MultipartFile file, long lessonId) throws FileUploadException {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Урок с ID " + lessonId + " не найден"));

        try {
            String originalName = file.getOriginalFilename();
            String hashedUrl = DigestUtils.sha1Hex(System.nanoTime() + originalName); // лучше, чем id (он ещё null)

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(hashedUrl)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            Attachment attachment = new Attachment();
            attachment.setName(originalName);
            attachment.setUrl(hashedUrl);
            attachment.setLesson(lesson);
            attachment.setCreatedTime(LocalDateTime.now());

            Attachment saved = fileRepository.save(attachment);

            return entityMapping.toDto(saved); // красиво: возвращаем DTO

        } catch (Exception e) {
            throw new FileUploadException("Не удалось загрузить файл: " + file.getOriginalFilename(), e);
        }
    }


    public byte[] downloadFile(String fileName) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .build()
            ).transferTo(outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new FileDownloadException("Ошибка при скачивании файла: " + fileName);
        }
    }

    public List<AttachmentDto> getFileList() {
        return entityMapping.toDto(fileRepository.findAll());
    }



}

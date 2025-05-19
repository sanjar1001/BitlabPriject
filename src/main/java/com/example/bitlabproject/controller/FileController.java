package com.example.bitlabproject.controller;

import com.example.bitlabproject.dto.AttachmentDto;
import com.example.bitlabproject.service.impl.FileService;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;


    @PostMapping("/upload/file/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public AttachmentDto uploadFile(@RequestParam(name = "file") MultipartFile file, @PathVariable long id) throws FileUploadException {

        return fileService.uploadFile(file, id);

    }

    @GetMapping("/download/file/{fileName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public ResponseEntity<byte[]> download(@PathVariable String fileName) {
        byte[] fileData = fileService.downloadFile(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileData);
    }

    @GetMapping("/get/list/file")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public List<AttachmentDto> getListFile() {
        return fileService.getFileList();
    }


}
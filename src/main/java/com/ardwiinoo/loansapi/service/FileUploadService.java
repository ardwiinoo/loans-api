package com.ardwiinoo.loansapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileUploadService {
    String uploadFile(MultipartFile file, String fileName);
    void deleteFile(String fileName);
}

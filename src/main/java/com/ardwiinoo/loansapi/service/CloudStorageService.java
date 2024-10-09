package com.ardwiinoo.loansapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudStorageService {

    String uploadFileTogCS(MultipartFile file, String dir);
    void deleteFileFromGcs(String url);
}
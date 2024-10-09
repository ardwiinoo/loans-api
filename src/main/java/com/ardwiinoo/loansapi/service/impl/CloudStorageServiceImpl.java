package com.ardwiinoo.loansapi.service.impl;

import com.ardwiinoo.loansapi.service.CloudStorageService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@Service
@Slf4j
public class CloudStorageServiceImpl implements CloudStorageService {

    private final Storage storage;
    private String bucketName = "..";

    public CloudStorageServiceImpl() throws IOException {
        this.storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream("path/to/serviceAccount.json")))
                .setProjectId("your-project-id")
                .build()
                .getService();
    }

    @Override
    public String uploadFileTogCS(MultipartFile file, String dir) {
        return null;
    }

    @Override
    public void deleteFileFromGcs(String url) {

    }
}

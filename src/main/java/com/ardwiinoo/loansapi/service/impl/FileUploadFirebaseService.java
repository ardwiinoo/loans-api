package com.ardwiinoo.loansapi.service.impl;

import com.ardwiinoo.loansapi.service.FileUploadService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class FileUploadFirebaseService implements FileUploadService {

    @Value("${firebase.storage.bucket.name}")
    private String bucketName;

    @Value("${firebase.credentials.file}")
    private String credentialsFile;

    private Storage storage;

    // inject ketika instance sudah siap
    @PostConstruct
    public void init() throws IOException {
        Credentials credentials = GoogleCredentials
                .fromStream(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(credentialsFile)));
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    @Override
    public String uploadFile(MultipartFile file, String fileName) {
        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            storage.create(blobInfo, file.getBytes());

            String downloadUrlTemplate = "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media";
            return String.format(downloadUrlTemplate, bucketName, URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Firebase", e);

        }
    }

    @Override
    public void deleteFile(String fileName) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        storage.delete(blobId);
    }
}

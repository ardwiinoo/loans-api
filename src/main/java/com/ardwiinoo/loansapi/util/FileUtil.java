package com.ardwiinoo.loansapi.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class FileUtil {

    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList(
            "application/pdf", "image/jpeg", "image/png",
            "application/x-pdf", "image/jpg"
    );

    public String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public boolean isAllowedFileType(MultipartFile file) {
        String contentType = file.getContentType();
        return ALLOWED_FILE_TYPES.contains(contentType);
    }
}

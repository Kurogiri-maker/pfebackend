package com.example.TalanCDZ.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class OcrService {
    public  boolean hasPDFFormat(MultipartFile file) {
        // Update the content type to match PDF files
        final String TYPE = "application/pdf";

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }
}

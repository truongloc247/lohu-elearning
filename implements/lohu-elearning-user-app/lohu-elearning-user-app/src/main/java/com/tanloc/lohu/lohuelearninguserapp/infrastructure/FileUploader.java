package com.tanloc.lohu.lohuelearninguserapp.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileUploader {
    public String uploadFile(MultipartFile multipartFile, String folder) throws IOException {
        Path uploadPath = Paths.get(folder);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString() + '_' + originalFileName;

        Path filePath = uploadPath.resolve(newFileName);
        Files.copy(multipartFile.getInputStream(), filePath,  StandardCopyOption.REPLACE_EXISTING);

        return "/flashcard_image/" + newFileName;
    }
}

package com.yogesh.ecom.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        // File name of current/original file
        String originalFileName = image.getOriginalFilename();
        // Generate a unique file name
        String fileName = System.currentTimeMillis() + "_" + originalFileName;
        String filePath = path + File.separator + fileName;
        // check if path exists or not and create
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // upload file to folder
        Files.copy(image.getInputStream(), new File(filePath).toPath());
        return fileName;
    }

}

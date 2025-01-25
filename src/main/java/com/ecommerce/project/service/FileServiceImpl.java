package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) {
        //File names of the original file
        String originalFileName = file.getOriginalFilename();
        //generate a unique file name
        String randomId = UUID.randomUUID().toString();
        //1234 --- mat.jpg --- 1234.jpg
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.pathSeparator + fileName;  // path+'/'+filename
        //Check if path exist and create
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
        //upload to server
        try {
            Files.copy(file.getInputStream(), Paths.get(filePath));
        } catch (IOException e) {
            throw new APIException(e.getMessage());
        }
        return fileName;
    }
}

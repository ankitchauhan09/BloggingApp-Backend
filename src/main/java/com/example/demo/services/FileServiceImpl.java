package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

//        get File name
        String name = file.getOriginalFilename();
//        generate random file id
        String randdomId = UUID.randomUUID().toString();
        String newFileName = randdomId.concat(name.substring(name.lastIndexOf(".")));
        String filePath = path  + newFileName;
        System.out.println("This is the full path + " + filePath);
        File f = new File(path);
        if(!f.exists()){
            System.out.println("creating directory");
            f.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
        }


    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }
}

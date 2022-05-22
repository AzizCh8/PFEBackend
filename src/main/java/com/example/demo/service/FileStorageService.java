package com.example.demo.service;

import com.example.demo.dao.FileRepository;
import com.example.demo.entities.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Autowired
    private FileRepository fileRepository;

    public File store(MultipartFile file,int nb) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

//        if (file.getContentType()!="text/plain" && file.getContentType()!="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
//        return null;
        File FileDB = new File(fileName, file.getContentType(), file.getBytes(), nb);
        return fileRepository.save(FileDB);
    }
    public File getFile(String id) {
        return fileRepository.findById(id).get();
    }

    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }

    public String getFiles(String filename){return fileRepository.findByName_file(filename);}

    public File getFiless(String filename){return fileRepository.findByName_filee(filename);}




    public File findByProcessus(Long id){return fileRepository.findByProcessus(id);}

    public List<String> findFilesByProcessus(Long id){return fileRepository.findFilesByProcessus(id);}

}
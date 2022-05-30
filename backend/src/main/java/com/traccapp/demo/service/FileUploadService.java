package com.traccapp.demo.service;

import com.traccapp.demo.payload.response.FileResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
    
    public FileResponse addFile(MultipartFile file){

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")){
                throw new IllegalStateException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            FileResponse fileResponse = new FileResponse();
            fileResponse.setFileName(fileName);
            fileResponse.setFileType(file.getContentType());
            fileResponse.setFileSize(file.getSize());
            
        }

        
    }
}
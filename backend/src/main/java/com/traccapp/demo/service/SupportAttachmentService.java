package com.traccapp.demo.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.exception.FileUploadException;
import com.traccapp.demo.model.SupportAttachments;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.repository.SupportAttachmentRepository;
import com.traccapp.demo.repository.SupportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SupportAttachmentService {

    @Autowired
    private final SupportAttachmentRepository supportAttachmentRepository;
    @Autowired
    private final SupportRepository supportRepository;

    public List<SupportAttachments> getAllFilesForsupport(Supports support){
        return supportAttachmentRepository.findAllBySupport(support);
    }

    public SupportAttachments getFile(UUID attachmentId){
        return supportAttachmentRepository.findById(attachmentId)
            .orElseThrow(() -> new AbstractGraphQLException("Attachment with current id cannot be found: "+attachmentId,"attachmentId"));
    }

    public SupportAttachments addFile(MultipartFile file, UUID supportId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{

            Supports support = supportRepository.findById(supportId)
                .orElseThrow(() -> new AbstractGraphQLException("support with current id cannot be found: "+supportId, "supportId"));

            if(fileName.contains("..")){
                throw new FileUploadException("Filename contains invalid path sequence: " + fileName);
            }

            SupportAttachments supportAttachments = new SupportAttachments();
            supportAttachments.setSupport(support);
            supportAttachments.setFileName(fileName);
            supportAttachments.setFileType(file.getContentType());
            supportAttachments.setFileSize(file.getSize());

            String encodedString = Base64.getEncoder().encodeToString(file.getBytes());
            supportAttachments.setFileBase64(encodedString);

            return supportAttachmentRepository.save(supportAttachments);
            
        } catch (IOException exception){
            throw new FileUploadException("Could not add the current file: "+fileName, exception);
        }
    }
}

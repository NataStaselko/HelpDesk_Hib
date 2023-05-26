package com.final_project.staselko.service;

import com.final_project.staselko.model.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService{
    void saveAllAttachment(MultipartFile[] files, Long ticketId);
    List<Attachment> getAllAttachment(Long ticketId);
    void deleteAttachment(Long attachmentId);
     Attachment getAttachmentById(Long attachmentId);


}

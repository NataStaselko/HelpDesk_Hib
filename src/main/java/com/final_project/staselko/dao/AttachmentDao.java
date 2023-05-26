package com.final_project.staselko.dao;

import com.final_project.staselko.model.entity.Attachment;

import java.util.List;
import java.util.Optional;

public interface AttachmentDao {
    void save(Attachment attachment);
    List<Attachment> getAllAttachment(String param, Object o);

    void delete(Attachment attachmentId);

    Optional<Attachment> findById(Long attachmentId);
}

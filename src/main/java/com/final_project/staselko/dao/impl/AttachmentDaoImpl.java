package com.final_project.staselko.dao.impl;

import com.final_project.staselko.dao.AttachmentDao;
import com.final_project.staselko.dao.HibernateDao;
import com.final_project.staselko.model.entity.Attachment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AttachmentDaoImpl extends HibernateDao<Attachment> implements AttachmentDao {

    public AttachmentDaoImpl() {
        setModelClass(Attachment.class);
    }

    @Override
    public void save(Attachment attachment) {
        create(attachment);
    }

    @Override
    public List<Attachment> getAllAttachment(String param, Object o) {
        return getAllEntity(param, o);
    }

    @Override
    public void delete(Attachment attachment) {
        deleteEntity(attachment);
    }

    @Override
    public Optional<Attachment> findById(Long attachmentId) {
        return findEntityById(attachmentId);
    }
}

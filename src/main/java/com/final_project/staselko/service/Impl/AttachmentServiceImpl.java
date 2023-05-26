package com.final_project.staselko.service.Impl;

import com.final_project.staselko.dao.AttachmentDao;
import com.final_project.staselko.model.entity.Attachment;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.exception.ResourceNotFoundException;
import com.final_project.staselko.service.AttachmentService;
import com.final_project.staselko.service.HistoryService;
import com.final_project.staselko.service.TicketService;
import com.final_project.staselko.utils.DescriptionHistoryCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentDao attachmentDao;
    private final TicketService ticketService;
    private final HistoryService historyService;
    private final DescriptionHistoryCreator descriptionCreator;
    @Transactional
    @Override
    public void saveAllAttachment(MultipartFile[] files, Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        for (MultipartFile file : files) {
            if (file.getSize() / 1048576 > 5) {
                throw new MaxUploadSizeExceededException(5242880L);
            } else {
                Attachment attachment = new Attachment();
                attachment.setName(file.getOriginalFilename());
                try {
                    attachment.setBlob(file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                attachment.setTicket(ticket);
                attachmentDao.save(attachment);
                historyService.addHistory(ticket, ticket.getOwner(),
                        descriptionCreator.actionAddFile(),
                        descriptionCreator.descriptionAddFile(attachment.getName()));
            }
        }
    }
    @Transactional
    @Override
    public List<Attachment> getAllAttachment(Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        return attachmentDao.getAllAttachment("ticket", ticket);
    }
    @Transactional
    @Override
    public void deleteAttachment(Long attachmentId) {
        Attachment attachment = attachmentDao.findById(attachmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with id " + attachmentId));
        Ticket ticket = attachment.getTicket();
        historyService.addHistory(ticket, ticket.getOwner(),
                descriptionCreator.actionRemoveFile(),
                descriptionCreator.descriptionRemoveFile(attachment.getName()));
        attachmentDao.delete(attachment);
    }
    @Transactional
    @Override
    public Attachment getAttachmentById(Long attachmentId) {
        return attachmentDao.findById(attachmentId).orElseThrow();
    }

}

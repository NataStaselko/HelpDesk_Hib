package com.final_project.staselko.service.mail;

import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.entity.User;

public interface MailService {
    void sendMailToUser(User user, Ticket ticket);

}

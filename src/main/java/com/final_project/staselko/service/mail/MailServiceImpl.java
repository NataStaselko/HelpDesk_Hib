package com.final_project.staselko.service.mail;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.utils.email.EmailCreator;
import com.final_project.staselko.utils.email.EmailTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine engine;
    private final EmailCreator emailCreator;


    @Override
    public void sendMailToUser(User user, Ticket ticket) {
        if (emailCreator.getEmailTemplate(user, ticket).isPresent()){
           EmailTemplate emailTemplate = emailCreator.getEmailTemplate(user, ticket).orElseThrow(); //дописать эксэпшен!!!!!

            final Context context = new Context();
            context.setVariable("subject", emailTemplate.getSubject());
            context.setVariable("textPart1", emailTemplate.getTextPart1());
            context.setVariable("textPart2", emailTemplate.getTextPart2());
            context.setVariable("href", emailTemplate.getHref());
            context.setVariable("textPart3", emailTemplate.getTextPart3());

            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            try {
                final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setFrom("BabaYaga-Shop@yandex.ru");
                message.setTo(emailTemplate.getEmails());
                message.setSubject("BabaYaga-Shop");
                final String content = this.engine.process("mail", context);
                message.setText(content, true);
            } catch (MessagingException e) {
            }
            this.mailSender.send(mimeMessage);
        }
    }

}
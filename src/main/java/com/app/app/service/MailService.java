package com.app.app.service;

import com.app.app.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailService {

    private final Logger logger;
    private final JavaMailSender mailSender;


    @Inject
    public MailService(JavaMailSender mailSender) {
        this.logger = LogManager.getLogger(getClass());
        this.mailSender = mailSender;
    }


    public List<Message> sendMessages(List<Message> messages) {
        List<Message> sentMessages = new ArrayList<>();

        messages.forEach(message -> {
            MimeMessage mail = createMail(message);
            mailSender.send(mail);
            sentMessages.add(message);
        });

        return sentMessages;
    }

    private MimeMessage createMail(Message message) {
        MimeMessage mail = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(message.getEmail());
            helper.setReplyTo("do-not-reply@example.com");
            helper.setFrom("do-not-reply@example.com");
            helper.setSubject(message.getTitle());
            helper.setText(message.getContent());
        } catch (MessagingException e) {
            handleMessagingException(e);
        }

        return mail;
    }

    private void handleMessagingException(MessagingException exception) {
        logger.error("Error during creating mail: " + exception.getMessage(), exception);
    }
}
package com.leaf.eexamen.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailSenderService {


    private JavaMailSender javaMailSender;


    @Autowired
    public MailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailWithPlainText(String address, String subject, String content) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(address);

        msg.setSubject(subject);
        msg.setText(content);

        javaMailSender.send(msg);

    }

    public void sendEmailWithPlainTextAndAttachment(String address, String subject, String content, DataSource dataSource, String reportName) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(address);
        helper.setSubject(subject);
        helper.setFrom("ocert.certificatie@gmail.com");

        helper.setText(content, false);

        helper.addAttachment(reportName, dataSource);

        javaMailSender.send(message);

    }
}

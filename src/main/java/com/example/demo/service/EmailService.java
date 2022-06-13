package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    private String clientEmail;

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendEmailFrom(String from,String to,String object,String message)throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(object);
        mail.setText(message);

        javaMailSender.send(mail);
        System.out.println("message sent");
    }

    public void sendEmail(String to,String object,String message) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("chebbahaziz5@gmail.com");
        mail.setSubject(object);
        mail.setText(message);

        javaMailSender.send(mail);
        System.out.println("message sent");
    }
    public void sendEmailAttachement(String to, String object, String message, String fileAttach) throws MailException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        FileSystemResource file = new FileSystemResource(new File("c:\\users\\asus\\downloads\\"+fileAttach));
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setFrom("chebbahaziz5@gmail.com");
            helper.setText(message);
            helper.setSubject(object);
            helper.addAttachment(file.getFilename(), file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            this.javaMailSender.send(mimeMessage);
        } catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}

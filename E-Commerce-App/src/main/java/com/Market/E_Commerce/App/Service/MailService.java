package com.Market.E_Commerce.App.Service;

import com.Market.E_Commerce.App.Model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;

    public void sendMail(Mail mail){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mail.getReceiver());
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
        mailSender.send(message);

    }
}

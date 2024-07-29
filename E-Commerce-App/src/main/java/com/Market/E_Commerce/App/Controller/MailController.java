package com.Market.E_Commerce.App.Controller;

import com.Market.E_Commerce.App.Model.Mail;
import com.Market.E_Commerce.App.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    MailService mailService;

    @PostMapping("/send")
    public void sendMail(Mail mail){

        mailService.sendMail(mail);
    }
}

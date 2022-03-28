package com.example.investbeacon.services;

import com.example.investbeacon.models.EducationPost;
import com.example.investbeacon.models.ForumPost;
import com.example.investbeacon.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailService {
    @Autowired
    public JavaMailSender emailSender;

    @Value("${spring.mail.from}")
    private String from;

    public void prepareAndSendUserWelcome(User user) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(user.getEmail());
        msg.setSubject("Welcome to Invest Beacon");
        msg.setText("Welcome to a community that likes to learn, contribute, and share ideas. Hope you find something that interests you and helps you grow.\n \n- Invest Beacon");

        try{
            this.emailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
    public void prepareAndSendForumPost(ForumPost post) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(post.getUser().getEmail());
        msg.setSubject("Invest Beacon - new comment!");
        msg.setText("A user has commented on your post. Log in to investbeacon.net to check it out.\n \n - Invest Beacon");

        try{
            this.emailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }



}

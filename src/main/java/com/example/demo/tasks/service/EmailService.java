package com.example.demo.tasks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendTaskAssignedEmail(String email, String username, String taskName){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("New Task Assigned");
        message.setText("Hello " + username + ",\n\n" +
                        "A new task has been assigned to you:\n\n" +
                        taskName
        );

        mailSender.send(message);
    }
}

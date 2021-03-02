package com.igwines.helper;

import com.igwines.dto.GenerateUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmailSender {

    private final JavaMailSender javaMailSender;

    @Value("${email.subject}")
    private String subject;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmail(GenerateUserDetails generateUserDetails, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(generateUserDetails.getEmail());
        message.setSubject(subject);
        message.setText(code);
        javaMailSender.send(message);
    }
}

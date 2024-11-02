package com.ardwiinoo.loansapi.service.impl;

import com.ardwiinoo.loansapi.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setFrom("no.reply.testing.ardwiinoo@gmail.com");
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(mimeMessage);

            log.info("Sent email to {}", to);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @Override
    public String buildVerificationEmail(String verificationUrl, String firstName) {
        return "<h1>Account Verification</h1>" +
                "<p>Hi " + firstName + ",</p>" +
                "<p>Thank you for registering. Please click the link below to verify your account:</p>" +
                "<a href=\"" + verificationUrl + "\">Verify Account</a>" +
                "<p>If you didn't create this account, please ignore this email.</p>";
    }
}

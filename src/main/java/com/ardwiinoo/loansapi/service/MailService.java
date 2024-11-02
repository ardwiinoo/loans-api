package com.ardwiinoo.loansapi.service;

public interface MailService {
    void sendEmail(String to, String subject, String body);
    String buildVerificationEmail(String verificationUrl, String firstName);
}

package net.cloud.component;

public interface MailService {
    void sendMail(String to, String subject, String content);
}

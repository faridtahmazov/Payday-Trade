package com.expressbank.email;

public interface EmailSender {
    void sendVerifyAccountMsg(String to, String email);

    void sendNotifications(String to, String email);
}

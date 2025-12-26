package com.eventapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendBookingEmail(String toEmail, String eventName) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Event Booking Confirmation");
        message.setText(
                "Your booking for event '" + eventName + "' is confirmed.\n\nThank you!"
        );

        mailSender.send(message);
    }
}

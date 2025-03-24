package com.secure.FileShareApp.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

@Service
@RequiredArgsConstructor
public class EmailUtils {

    private final JavaMailSender mailSender;

    public void sendEmailWithStreamAttachment(String to, String subject, String body, String fileUrl, String fileName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.setFrom("hello@demomailtrap.co");

            System.out.println("sendEmailWithStreamAttachment");
            URL url = URI.create(fileUrl).toURL();
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close(); // Close InputStream after reading into ByteArrayOutputStream
            byte[] fileBytes = outputStream.toByteArray();

            helper.addAttachment(fileName, new ByteArrayResource(fileBytes));
            System.out.println("Sending email with attachment...");
            mailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to attach file from Cloudinary" + e.getMessage());
        } catch (MalformedURLException e) {
            throw new RuntimeException(" Failed to attach file from Cloudinary due to url exception" + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("IO Exception while attaching file from Cloudinary" + e.getMessage());
        } catch (MailSendException e){
            throw new RuntimeException("Unable to send email due to " + e.getMessage());
        }

    }
}

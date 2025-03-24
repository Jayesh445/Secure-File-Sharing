package com.secure.FileShareApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${spring.mail.default-encoding}")
    private String defaultEncoding;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttlsEnable;

//    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
//    private int connectionTimeout;
//
//    @Value("${spring.mail.properties.mail.smtp.timeout}")
//    private int timeout;
//
//    @Value("${spring.mail.properties.mail.smtp.writetimeout}")
//    private int writeTimeout;
//
//    @Value("${spring.mail.properties.mail.smtp.socketFactory.port}")
//    private int socketFactoryPort;
//
//    @Value("${spring.mail.properties.mail.smtp.socketFactory.class}")
//    private String socketFactoryClass;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);
        mailSender.setDefaultEncoding(defaultEncoding);

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.starttls.enable", starttlsEnable);
//        properties.put("mail.smtp.connectiontimeout", connectionTimeout);
//        properties.put("mail.smtp.timeout", timeout);
//        properties.put("mail.smtp.writetimeout", writeTimeout);
//        properties.put("mail.smtp.socketFactory.port", socketFactoryPort);
//        properties.put("mail.smtp.socketFactory.class", socketFactoryClass);
        return mailSender;
    }
}

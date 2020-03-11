package com.vn.config;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;

public class GoogleMailSender {
    protected String host = "smtp.gmail.com";
    protected Integer sslPort = 465;
    protected Integer smtpPort = 465;
    protected boolean smtpAuth = true;
    protected String socketClass = "javax.net.ssl.SSLSocketFactory";
    protected String authUserName = "tanbv.dev@gmail.com";
    protected String authPassword = "abcABC1234";

    public void sendSimpleMailWarningTLS(String from, String to, String subject, String message)
            throws Exception {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(authUserName, authPassword);
            }
        });

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(subject, "UTF-8");
        msg.setContent(message, "text/html; charset=UTF-8");

        Transport.send(msg);
    }
}

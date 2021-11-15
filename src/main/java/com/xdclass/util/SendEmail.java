package com.xdclass.util;

import com.xdclass.base.Testbase;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @program : xdclassAPITest
 * @ Author      ：Fanyong Kong
 * @ Date        ：Created in 21:47 2021/11/14 2021
 * @ Description ：This is to send email of test result.
 * @Version : 1.0
 */
public class SendEmail {

    /**
     * @Description: This is to send email!
     * @params: [title, body]
     * @return: void
     * @Author: Fanyong Kong
     * @Date: 2021/11/14
     */
    public static void sendEmail(String title, String body) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", Testbase.getProperty("mailHost"));
        properties.put("mail.smtp.port", Testbase.getProperty("mailPort"));
        properties.setProperty("mail.smtp.socketFactory.port", Testbase.getProperty("mailPort"));
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "SSL_FACTORY");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Testbase.getProperty("mailSender"), Testbase.getProperty("mailAutoCode"));
            }
        });
        session.setDebug(true);
        System.out.println("Creating mail");

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Testbase.getProperty("mailSender")));
        message.setRecipients(Message.RecipientType.TO, Testbase.getProperty("mailReceivers"));
//		message.setRecipients(Message.RecipientType.CC, MY_EMAIL_ACCOUNT);

        message.setSubject(title);
        message.setContent(body, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        System.out.println("Sending mail");
        Transport.send(message);
    }



}
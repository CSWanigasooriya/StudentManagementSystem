/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Chamath Wanigasooriya
 * User: 36-se-0004@kdu.ac.lk
 * Date: 29/10/2020
 */
 
public class Mail {
        final String username = "";//Please enter your office 365 or exchange account address
        final String passwd = "";//Enter your Password Here
        final String from = "";//Please enter your office 365 or exchange account address
        private final String to;
        private final String subject;
        private final String message;

    public Mail() {
        this.to="";
        this.subject="";
        this.message="";
    }
    public Mail(String reciever,String subject,String message){
        this.to=reciever;
        this.subject=subject;
        this.message=message;
    }
    public void doTest() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, passwd);
            }
        });


        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,
                    to);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(message);
            Transport.send(msg);

        } catch (MessagingException e) {
            System.out.println("send failed, exception: " + e);
        }
        System.out.println("Sent Ok");
    }

}
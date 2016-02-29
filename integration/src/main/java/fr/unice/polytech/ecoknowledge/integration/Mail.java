package fr.unice.polytech.ecoknowledge.integration;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Sebastien on 23/02/2016.
 */
public class Mail {

    public static void main(String args[]){

        String file = args[0];
        String addresses = args[1];

        String content ="";
        String subject ="";

        File log = new File(file);
        DataInputStream dis =
                null;
        try {
            dis = new DataInputStream(
                    new FileInputStream(log));
            byte[] datainBytes = new byte[dis.available()];
            dis.readFully(datainBytes);
            dis.close();
            content = new String(datainBytes, 0, datainBytes.length);
            subject = content.contains("FAIL")?"[FAIL] pfe-ecoknowledge Integration":"[SUCCESS] pfe-ecoknowledge Integration";
            subject+= " " + new Date().toString();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        sendMail(subject, content, addresses);
    }

    public static void sendMail(String subject, String text, String addresses)
    {

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "pfe.ecoknowledge@gmail.com");
        props.put("mail.smtp.password", "hugoBosse");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");


        Session session = Session.getInstance(props,null);
        MimeMessage message = new MimeMessage(session);

        System.out.println("Port: "+session.getProperty("mail.smtp.port"));

        // Create the email addresses involved
        try {
            InternetAddress from = new InternetAddress("pfe.ecoknowledge@gmail.com");
            message.setSubject(subject);

            message.setFrom(from);
            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(addresses));

            // Create a multi-part to combine the parts
            Multipart multipart = new MimeMultipart("alternative");

            // Create your text message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(text);

            // Add the text part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Add html part to multi part
            multipart.addBodyPart(messageBodyPart);

            // Associate multi-part with message
            message.setContent(multipart);

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
            transport.sendMessage(message, message.getAllRecipients());


        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

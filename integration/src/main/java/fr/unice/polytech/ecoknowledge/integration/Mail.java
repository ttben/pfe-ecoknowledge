package fr.unice.polytech.ecoknowledge.integration;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * Created by Sébastien on 23/02/2016.
 */
public class Mail {
    
    public static void sendMail(String subject, String text, String addresses)
    {
        Properties accountProperties = new Properties();

        try {

            //Locale locale = new Locale("fr", "FR");
            //Locale.setDefault(locale);
            InputStream inputStream = new FileInputStream(
                    URLDecoder.decode(
                            Mail.class.getClassLoader().getResource("account.properties").toString().replace("file:", "")
                            , "UTF-8"));
            accountProperties.load(inputStream);
        } catch (FileNotFoundException e ) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "pfe.ecoknowledge@gmail.com");
        props.put("mail.smtp.password", accountProperties.getProperty("password"));
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
            messageBodyPart.setText("");

            // Add the text part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Create the html part
            messageBodyPart = new MimeBodyPart();
            String htmlMessage = text;
            messageBodyPart.setContent(htmlMessage, "text/html");


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

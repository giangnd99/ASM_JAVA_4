package util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailUtil {
    private final String HOST_EMAIL = "nguyendanggiang99@gmail.com";
    private final String PASSWORD = "bmdl exir oumv ipsk";
    private String[] recipients;
    private String[] ccReciptients;
    private String[] bccReciptients;
    private String subject;
    private String messageBody;
    private Multipart attachments;

    public boolean sendEmail(EmailUtil email) {
        
        try {
            Session session = createSession();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(HOST_EMAIL));
            message.setRecipients(Message.RecipientType.TO, convertToAddresses(recipients));
            if (email.getCcReciptients() != null)
                message.setRecipients(Message.RecipientType.CC, convertToAddresses(email.getCcReciptients()));
            if (email.getBccReciptients() != null)
                message.setRecipients(Message.RecipientType.BCC, convertToAddresses(email.getBccReciptients()));
            message.setSubject(email.getSubject());

            if (email.getAttachments() != null) {
                message.setContent(email.getAttachments(), "text/html; charset=utf-8");
            } else {
                message.setContent(email.getMessageBody(), "text/html; charset=utf-8");
            }

            Transport.send(message);
            System.out.println("Email sent successfully");
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    private Session createSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");

        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(HOST_EMAIL, PASSWORD);
            }
        });
    }

    private Address[] convertToAddresses(String[] emails) throws AddressException {
        Address[] addresses = new Address[emails.length];
        for (int i = 0; i < emails.length; i++) {
            addresses[i] = new InternetAddress(emails[i].trim());
        }
        return addresses;
    }
}

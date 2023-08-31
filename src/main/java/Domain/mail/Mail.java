package Domain.mail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;



public class Mail {

    String emailFrom = "appconcesionario@gmail.com";
    String passwordFrom ="ywbnhawcrslcebwr";


    public void createEmail(String password, String email, String name) throws MessagingException {


        Properties mProperties = new Properties();

        mProperties.put("mail.smtp.host","smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable","true");
        mProperties.put("mail.smtp.ssl.trust","smtp.gmail.com");
        mProperties.setProperty("mail.smtp.port","465");
        mProperties.put("mail.smtp.starttls.required", "true");
        mProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.user",emailFrom);
        mProperties.setProperty("mail.smtp.auth","true");
        mProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session=Session.getDefaultInstance(mProperties);


        String subject= "Recuperar correo APP CONCESIONARIO";


        MimeMessage message = new MimeMessage(session);


            message.setFrom(new InternetAddress(emailFrom));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText("El usuario para acceder a su cuenta APP CONCESIONARIO es:\n Nombre: " + name + "\n Password: " + password );



                Transport mTransport = session.getTransport("smtp");
                 mTransport.connect(emailFrom,passwordFrom);
                mTransport.sendMessage(message, message.getAllRecipients());
                mTransport.close();


    }


}

package Fixture;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;


public class EmailsHandling {

    public static void main(String[] args) throws MessagingException {

        String host = "imap.gmail.com";
        String username = "user";
        String password = "passwd";
        Properties props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");
        // set any other needed mail.imap.* properties here
        Session session = Session.getInstance(props);
        Store store = session.getStore("imap");
        store.connect(host, username, password);

    }






}

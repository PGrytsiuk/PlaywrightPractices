package Hooks;

import Configs.ConfigLoader;
import Pages.ResetPassword;
import Utils.PasswordGenerator;
import com.microsoft.playwright.Page;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SubjectTerm;
import java.net.MalformedURLException;
import java.util.Properties;


public class EmailsHandlingResetPasswordFlow {

    protected Page page;

    public EmailsHandlingResetPasswordFlow(Page page) {
        this.page = page;
    }


    public void executeResetPasswordMail() throws Exception {

        ConfigLoader config = new ConfigLoader();
        String emailUsername = config.getProperty("Email_username");
        String emailPassword = config.getProperty("Email_password");

        String host = "imap.gmail.com";
        Properties props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");
        // set any other needed mail.imap.* properties here
        Session session = Session.getInstance(props);
        Store store = session.getStore("imap");



        try {
            store.connect(host, emailUsername, emailPassword);

            // Access inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Fetch new messages from the server
            Message[] messages = inbox.search(new SubjectTerm("Recover password on LangFit"));
            if (messages.length == 0) {
                System.out.println("No messages found with the specified subject.");
                return;
            }

            Message message = messages[0];
            String content = getTextFromMessage(message);

            // Extract the reset link from the email content
            String resetLink = extractResetLink(content);

            if (resetLink != null) {
                completePasswordReset(resetLink);
            } else {
                System.out.println("Reset password link not found in the email content.");
            }

        } catch (MessagingException e) {
            System.err.println("Failed to connect to the email server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (store != null && store.isConnected()) {
                store.close();
            }
        }
    }

    private static String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            return getTextFromMimeMultipart((MimeMultipart) message.getContent());
        }
        return "";
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent());
                break; // Avoid returning the same text content twice.
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result.append(html);
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();

    }

    private static String extractResetLink(String content) {
        // Regex to extract URLs within angle brackets or in href="..."
        String regex = "href\\s*=\\s*\"([^\"]*)\"|<\\s*([^>\\s]+)\\s*>";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String url = null;
            if (matcher.group(1) != null) {
                url = matcher.group(1);
            } else if (matcher.group(2) != null) {
                url = matcher.group(2);
            }

            if (url != null && isValidUrl(url)) {
                return url;
            }
        }

        return null;
    }

    private static boolean isValidUrl(String url) {
        try {
            new java.net.URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public void completePasswordReset(String resetLink) {
        try {
            ResetPassword resetPassword = new ResetPassword(page);
            String newPassword = PasswordGenerator.generateUniquePassword();

            // Load the reset link
            page.navigate(resetLink);
            //Verify in New Password title is present
            if(resetPassword.newPasswordTitle()){
                resetPassword.assertNewPasswordtitle("New password");
            }else
                System.out.println("New password title is not visible");
            //Verify that Send button is blocked by default
            resetPassword.SendButtonDisabledbyDefault();
            //Fill New password and confirm password fields
            resetPassword.EnteringNewPassword(newPassword, newPassword);


        } catch (Exception e) {
            System.err.println("Failed to complete the password reset process: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
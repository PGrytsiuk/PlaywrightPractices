package com.langfit.tests.specific.hooks;

import com.langfit.data.TestData;
import langfit.web.pages.ResetPassword;
import utils.PasswordGenerator;
import com.langfit.test.fixture.TestInitializer;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;

import org.testng.annotations.BeforeMethod;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SubjectTerm;
import java.util.Arrays;
import java.util.Properties;

public class EmailsHandlingResetPasswordFlow {

    protected Page page;
    protected Browser browser;

    public EmailsHandlingResetPasswordFlow(Page page) {
        this.page = page;
        setUpTest();
    }

    public void executeResetPasswordMail() throws Exception {

        if (TestData.getEmailUsername() == null || TestData.getLastPassword() == null) {
            throw new IllegalArgumentException("Email_username or Email_password is not set in the configuration.");
        }

        String host = "imap.gmail.com";
        Properties props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");
        Session session = Session.getInstance(props);
        Store store = session.getStore("imap");

        try {
            store.connect(host, TestData.getEmailUsername(), TestData.getEmailPassword());

            // Access inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Fetch new messages from the server
            Message[] messages = inbox.search(new SubjectTerm("Recover password on LangFit"));
            if (messages.length == 0) {
                System.out.println("No messages found with the specified subject.");
                return;
            }

            // Sort messages by received date in descending order (latest first)
            Arrays.sort(messages, (m1, m2) -> {
                try {
                    return m2.getReceivedDate().compareTo(m1.getReceivedDate());
                } catch (MessagingException e) {
                    return 0;
                }
            });

            // Use the latest message
            Message message = messages[0];
            String content = getTextFromMessage(message);

            // Debug: Print out email properties
            System.out.println("Content Type: " + message.getContentType());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Received Date: " + message.getReceivedDate());

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
        if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
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
                // Avoid returning the same text content twice.
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
        // Regex to extract URLs within angle brackets
        String regex = "(https?://u9534674\\.ct\\.sendgrid\\.net/ls/click\\?upn=[^\"]+)\" ";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            // Return the content of the first capturing group (inside the angle brackets)
            return matcher.group(1);
        }

        return null;
    }

    private ResetPassword resetPassword;
    private PasswordGenerator passwordGenerator;

    @BeforeMethod
    public void setUpTest() {
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the LoginPage object
       resetPassword = testInitializer.getResetPassword();
       passwordGenerator = testInitializer.getPasswordGenerator();
    }


    public void completePasswordReset(String resetLink) {
        String newPassword = passwordGenerator.generateUniquePassword();
        String confirmPassword = newPassword;
            // Load the reset link
            page.navigate(resetLink);
            // Verify in New Password title is present
            if (resetPassword.newPasswordTitle()) {
                resetPassword.assertNewPasswordTitle("New password");
            } else {
                System.out.println("New password title is not visible");
            }
            // Verify that Send button is blocked by default
            resetPassword.sendButtonDisabledByDefault();
            // Fill New password and confirm password fields
            resetPassword.enteringNewPassword(newPassword, confirmPassword);
            // Verify Success Toast for Rest Password journey
            resetPassword.successToastIsVisible();
            resetPassword.assertSuccessToast("Password successfully changed");

            System.out.println("Generated Password: " + newPassword);
    }
}
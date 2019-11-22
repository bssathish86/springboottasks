package com.springboot.microservices.authenticationservice.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class EmailProcessor {

	private static final String EMAIL_SUBJECT = "System generated user credential deatils.";

	private static final String EMAIL_CONTENT = "User credential valid only for 2 minutes ";

	public void sendMail(long generatedLoginId, String userEmail, String generatedPwd) {

		String sender = "sathishkumar_s@persistent.com";
		final String password = "Skamal86Skamal86";

		Session session = getSession(sender, password, getMailServerDetails());

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

			message.setSubject(EMAIL_SUBJECT);

			message.setContent("<H1 style='color: Green'>" + EMAIL_CONTENT + "</H1>\n\n"
					+ "<table style=\"color: gray\" border=\"1\">\r\n" + "<tr>\r\n"
					+ "<th style=\"color: blue\"> LoginId </th>\r\n" + "<th style=\"color: blue\"> Password </th>\r\n"
					+ "</tr>" + "<tr>\r\n" + "<td style=\"color: black\"> " + generatedLoginId + "</td>\r\n"
					+ "<td style=\"color: black\"> " + generatedPwd + "</td>\r\n" + "</tr>", "text/html");

			Transport.send(message);

			System.out.println("Mail successfully sent");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private Session getSession(String sender, final String password, Properties properties) {
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password);
			}
		});
		return session;
	}

	private Properties getMailServerDetails() {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.office365.com");
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.socketFactory.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.debug", "true");
		return properties;
	}
}

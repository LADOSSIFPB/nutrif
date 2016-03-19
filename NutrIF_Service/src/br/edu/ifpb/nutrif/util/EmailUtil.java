package br.edu.ifpb.nutrif.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.CursoDAO;

public class EmailUtil {

	private static Logger logger = LogManager.getLogger(CursoDAO.class);

	private class SMTPAuthenticator extends Authenticator {
		private PasswordAuthentication authentication;

		public SMTPAuthenticator(String login, String password) {
			authentication = new PasswordAuthentication(login, password);
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}

	public void send() {

		String from = "****@gmail.com";
		String to = "****@gmail.com";
		String subject = "Your Subject.";
		String message = "Message Text.";
		String login = "****@gmail.com";
		String password = "********";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

		Authenticator auth = new SMTPAuthenticator(login, password);

		Session session = Session.getInstance(props, auth);

		MimeMessage msg = new MimeMessage(session);

		try {
			
			msg.setText(message);
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			Transport.send(msg);
		
		} catch (MessagingException ex) {
			
			logger.error(ex);
		}
	}
}

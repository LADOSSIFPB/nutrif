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
import br.edu.ifpb.nutrif.exception.EmailExceptionNutrIF;

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

	public void sendChaveConfirmacaoAluno(String to, String keyConfirmation, String nome) 
			throws EmailExceptionNutrIF {
		
		String subject = "NutrIF - Ativação da conta do Aluno"; 
		String message = nome + ", bem-vindo(a) ao NutrIF! \n"
				+ "Sua chave de confirmação de acesso é " + keyConfirmation + ".\n"
				+ "Utilize para habilitar o acesso ao NutrIF Móvel para Android."
				+ " Acesse a opção - Validar Código -";
		
		this.send(to, subject, message);
	}
	
	public void send(String to, String subject, String message) 
			throws EmailExceptionNutrIF {

		String from = "******@gmail.com"; // Sender Account.

		final String smtpServer = "smtp.gmail.com";
		final String password = "*****"; // Password -> Application Specific Password.
		final String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
		final String smtpPort = "587";
		final String PORT = "465";

		final Properties props = new Properties();
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", "false");
		props.put("mail.smtp.socketFactory.port", PORT);
		props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");

		Authenticator auth = new SMTPAuthenticator(from, password);

		Session session = Session.getInstance(props, auth);

		MimeMessage mimeMessage = new MimeMessage(session);

		try {
			
			logger.info("Preparando o envio do e-mail para: " + to);
			
			mimeMessage.setText(message);
			mimeMessage.setSubject(subject);
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.addRecipient(Message.RecipientType.TO, 
					new InternetAddress(to));
			
			Transport transport = session.getTransport("smtp");
			transport.connect(smtpServer, from, password);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		
			logger.info("E-mail enviado com sucesso.");
			
		} catch (MessagingException messagingException) {
			
			throw new EmailExceptionNutrIF(messagingException);
		}
	}
}

package ar.edu.uade.pfi.pep.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.repository.document.user.User;

@Component
public class MailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

	@Value("${mail.service.account}")
	private String mailServiceAccount;

	@Value("${mail.service.password}")
	private String mailServicePassword;

	@Value("${mail.smtp.auth}")
	private String mailSmtpAuth;

	@Value("${mail.smtp.starttls.enable}")
	private String mailSmtpStarttlsEnable;

	@Value("${mail.smtp.host}")
	private String mailSmtpHost;

	@Value("${mail.smtp.port}")
	private String mailSmtpPort;

	private void sendMail(String to, String subjectMessage, String bodyMessage) {

		Runnable runnable = new Runnable() {
			@Override
			public void run() {

				try {
					Properties props = new Properties();
					props.put("mail.smtp.auth", mailSmtpAuth);
					props.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
					props.put("mail.smtp.host", mailSmtpHost);
					props.put("mail.smtp.port", mailSmtpPort);

					Session session = Session.getInstance(props, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(mailServiceAccount, mailServicePassword);
						}
					});

					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(mailServiceAccount));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
					message.setSubject(subjectMessage);
					message.setText(bodyMessage, "utf-8", "html");

					Transport.send(message);
				} catch (Exception e) {
					MailService.LOGGER.error(e.getMessage(), e);
				}
			}
		};
		new Thread(runnable).start();
	}

	public void sendMail(User user) {
		String subject = "";
		StringBuffer sb = new StringBuffer("<h1>Portal Educativo para Programar (pep)</h1>");
		String body = "";
		switch (user.getLastEvent().getType()) {
		case CREATION:
			subject = "Plataforma educativa para Programar (PEP): Creación de usuario";
			sb.append("<p>Hola!!<br></p>");
			sb.append("<p>Has registrado un usuario en el portal. Para completar el proceso debes ");
			sb.append("hacer click <a href='http://localhost:4200/user/activate/{{username}}/{{token}}'>aquí</a>.</p>");
			body = sb.toString();
			body = body.replace("{{username}}", user.getUsername());
			body = body.replace("{{token}}", user.getLastEvent().getToken());
			break;
		case UNLOCK:
			subject = "Plataforma educativa para Programar (PEP): Desbloquear usuario";
			sb.append("<p>Hola!!<br></p>");
			sb.append("<p>Has solicitado el desbloqueo de usuario. Para completar el proceso debes ");
			sb.append("hacer click <a href='http://localhost:4200/user/activate/{{username}}/{{token}}'>aquí</a>.</p>");
			body = sb.toString();
			body = body.replace("{{username}}", user.getUsername());
			body = body.replace("{{token}}", user.getLastEvent().getToken());
			break;

		case PASSWORD_RESET:
			subject = "Plataforma educativa para Programar (PEP): Blanqueo de contraseña";
			sb.append("<p>Hola!!<br></p>");
			sb.append("<p>Has solicitado el blanqueo de contraseña. Para completar el proceso debes ");
			sb.append("hacer click <a href='http://localhost:4200/user/reset/{{username}}/{{token}}'>aquí</a>.</p>");
			body = sb.toString();
			body = body.replace("{{username}}", user.getUsername());
			body = body.replace("{{token}}", user.getLastEvent().getToken());
			break;

		case REQUEST_ACTIVATION:
			subject = "Plataforma educativa para Programar (PEP): Activación de usuario";
			sb.append("<p>Hola!!<br></p>");
			sb.append("<p>Has solicitado la activacion del usuario. Para completar el proceso debes ");
			sb.append("hacer click <a href='http://localhost:4200/user/activate/{{username}}/{{token}}'>aquí</a>.</p>");
			body = sb.toString();
			body = body.replace("{{username}}", user.getUsername());
			body = body.replace("{{token}}", user.getLastEvent().getToken());
			break;
		}

		this.sendMail(user.getUsername(), subject, body);

	}
}

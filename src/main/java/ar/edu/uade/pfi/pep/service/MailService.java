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
import org.springframework.stereotype.Service;

import ar.edu.uade.pfi.pep.repository.document.Consultation;
import ar.edu.uade.pfi.pep.repository.document.Workspace;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Service
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

	public void sendMail(User user) {
		String subject = "";
		StringBuffer sb = new StringBuffer("<h1>Portal Educativo de Programación</h1>");
		String body = "";
		switch (user.getLastEvent().getType()) {
		case PENDING_ACTIVATION:
			subject = "Ayuda con la activación de su cuenta de PEP";
			sb.append("<p>Saludos del equipo de PEP,</p>");
			sb.append(
					"<p>Hemos recibido una solicitud para generar una cuenta de PEP asociada a esta dirección de correo electrónico. Haga clic en el enlace de abajo para activar la cuenta:</p>");
			sb.append(
					"<p><a href='http://localhost:4200/user/activate/{{username}}/{{token}}'>http://localhost:4200/user/activate/{{username}}/{{token}}</a></p>");
			sb.append(
					"<p>Si al hacer clic en el enlace no funciona, cópielo y péguelo en la barra de dirección del explorador web. Podrá acceder con su cuenta de PEP cuando haya hecho clic en el enlace anterior.</p>");
			sb.append(
					"<p>Si no solicitó una cuenta de PEP, puede hacer caso omiso de de este mensaje de correo electrónico.</p>");
			sb.append("<p>Gracias por usar PEP.</p>");
			sb.append("<p>Atentamente,</p>");
			sb.append("<p>El equipo de PEP - Portal Educativo de Programación</p>");
			body = sb.toString();
			body = body.replace("{{username}}", user.getUsername());
			body = body.replace("{{token}}", user.getLastEvent().getToken());
			break;
		case UNLOCK:
			subject = "Ayuda con la activación de su cuenta bloqueada de PEP";
			sb.append("<p>Saludos del equipo de PEP,</p>");
			sb.append(
					"<p>Hemos recibido una solicitud para activar una cuenta bloqueada de PEP asociada a esta dirección de correo electrónico. Haga clic en el enlace de abajo para activar la cuenta:</p>");
			sb.append(
					"<p><a href='http://localhost:4200/user/activate/{{username}}/{{token}}'>http://localhost:4200/user/activate/{{username}}/{{token}}</a></p>");
			sb.append(
					"<p>Si al hacer clic en el enlace no funciona, cópielo y péguelo en la barra de dirección del explorador web. Podrá acceder con su cuenta de PEP cuando haya hecho clic en el enlace anterior.</p>");
			sb.append(
					"<p>Si no solicitó la activación de una cuenta bloqueada de PEP, puede hacer caso omiso de de este mensaje de correo electrónico.</p>");
			sb.append("<p>Gracias por usar PEP.</p>");
			sb.append("<p>Atentamente,</p>");
			sb.append("<p>El equipo de PEP - Portal Educativo de Programación</p>");
			body = sb.toString();
			body = body.replace("{{username}}", user.getUsername());
			body = body.replace("{{token}}", user.getLastEvent().getToken());
			break;
		default:
			break;
		}

		this.sendMail(user.getUsername(), subject, body);
	}
	
	public void sendResponseConsultationMail(Consultation consultation) {
		
		String subject = "Han enviado una respuesta a tu cuenta PEP";
		StringBuffer sb = new StringBuffer("<h1>Portal Educativo de Programación</h1>");
		sb.append("<p>Saludos del equipo de PEP,</p>");
		sb.append("<p>El docente <i>{{teacher}}</i> ha enviado la siguiente respuesta:</p>");
		sb.append("<blockquote>{{response}}</blockquote >");
		sb.append("<p>Has click <a href='http://localhost:4200'>aquí</a> para ingresar al portal.</p>");
		sb.append("<p>Gracias por usar PEP.</p>");
		sb.append("<p>Atentamente,</p>");
		sb.append("<p>El equipo de PEP - Portal Educativo de Programación</p>");
		
		String body = sb.toString();
		body = body.replace("{{teacher}}", consultation.getTeacher().getUser().getName() + " " + consultation.getTeacher().getUser().getSurename());
		body = body.replace("{{response}}", consultation.getTeacherResponse());
		
		this.sendMail(consultation.getStudent().getUser().getUsername(), subject, body);
	}
	
	public void sendConsultationMail(Consultation consultation) {
		
		String subject = "Han enviado una consulta a tu cuenta PEP";
		StringBuffer sb = new StringBuffer("<h1>Portal Educativo de Programación</h1>");
		sb.append("<p>Saludos del equipo de PEP,</p>");
		sb.append("<p>El alumno <i>{{student}}</i> ha enviado la siguiente consulta:</p>");
		sb.append("<blockquote>{{consultation}}</blockquote>");
		sb.append("<p>Has click <a href='http://localhost:4200'>aquí</a> para ingresar al portal para respoder la consulta.</p>");
		sb.append("<p>Gracias por usar PEP.</p>");
		sb.append("<p>Atentamente,</p>");
		sb.append("<p>El equipo de PEP - Portal Educativo de Programación</p>");
		
		String body = sb.toString();
		body = body.replace("{{student}}", consultation.getStudent().getUser().getName() + " " + consultation.getStudent().getUser().getSurename());
		body = body.replace("{{consultation}}", consultation.getConsultation());
		
		this.sendMail(consultation.getTeacher().getUser().getUsername(), subject, body);
	}

	public void sendCorrectionMail(Workspace ws) {
		
		String subject = "Han enviado la corrección de un ejericio a tu cuenta PEP";
		StringBuffer sb = new StringBuffer("<h1>Portal Educativo de Programación</h1>");
		sb.append("<p>Saludos del equipo de PEP,</p>");
		sb.append("<p>El docente <i>{{teacher}}</i> ha enviado la corrección de un ejercicio.</p>");
		sb.append("<p>Para ver la corrección has click <a href='http://localhost:4200'>aquí</a> para ingresar al portal.</p>");
		sb.append("<p>Gracias por usar PEP.</p>");
		sb.append("<p>Atentamente,</p>");
		sb.append("<p>El equipo de PEP - Portal Educativo de Programación</p>");
		
		String body = sb.toString();
		body = body.replace("{{teacher}}", ws.getCourse().getTeacher().getUser().getName() + " " + ws.getCourse().getTeacher().getUser().getSurename());
		
		this.sendMail(ws.getStudent().getUser().getUsername(), subject, body);
	}
	
	public void sendNeedCorrectionMail(Workspace w) {
		String subject = "Han enviado un pedido de corrección de un ejericio a tu cuenta PEP";
		StringBuffer sb = new StringBuffer("<h1>Portal Educativo de Programación</h1>");
		sb.append("<p>Saludos del equipo de PEP,</p>");
		sb.append("<p>El alumno <i>{{studen}}</i> ha resuelto un ejercicio y requiere corrección.</p>");
		sb.append("<p>Para realizar la corrección has click <a href='http://localhost:4200'>aquí</a> para ingresar al portal.</p>");
		sb.append("<p>Gracias por usar PEP.</p>");
		sb.append("<p>Atentamente,</p>");
		sb.append("<p>El equipo de PEP - Portal Educativo de Programación</p>");
		
		String body = sb.toString();
		body = body.replace("{{teacher}}", w.getCourse().getTeacher().getUser().getName() + " " + w.getCourse().getTeacher().getUser().getSurename());
		
		this.sendMail(w.getStudent().getUser().getUsername(), subject, body);
		
	}
	
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
					MailService.LOGGER.info("Mail was sendded: to: " + to);

				} catch (Exception e) {
					MailService.LOGGER.error(e.getMessage(), e);
				}
			}
		};
		new Thread(runnable).start();
	}
}

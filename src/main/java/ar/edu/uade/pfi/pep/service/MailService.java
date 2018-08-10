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

	public void sendMail(String to, String subjectMessage, String bodyMessage) throws Exception {

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
					System.out.println("Inside : " + Thread.currentThread().getName());
				} catch (Exception e) {
					MailService.LOGGER.error(e.getMessage(), e);
				}
			}
		};
		new Thread(runnable).start();
	}
}

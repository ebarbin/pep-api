package ar.edu.uade.pfi.pep.service;

import java.util.Date;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.repository.UserRepository;
import ar.edu.uade.pfi.pep.repository.document.User;
import ar.edu.uade.pfi.pep.repository.document.UserAccountEvent;
import ar.edu.uade.pfi.pep.repository.document.UserAccountEventType;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailService mailService;

	public void register(User user) throws Exception {

		User existingUser = this.userRepository.findByUsername(user.getUsername());
		if (existingUser != null)
			throw new Exception("El email ingresado ya se encuentra registrado.");

		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

		user.setPassword(hashedPassword);
		user.setActive(Boolean.FALSE);
		user.setLoginAttempt(Integer.valueOf(0));
		user.setLastEvent(new UserAccountEvent());
		user.getLastEvent().setDate(new Date());
		user.getLastEvent().setType(UserAccountEventType.CREATION);
		user.getLastEvent().setToken(UUID.randomUUID().toString());

		this.userRepository.save(user);
		this.mailService.sendMail(user);
	}

	public void activate(String username, String token) throws Exception {

		User existingUser = this.userRepository.findByUsername(username);
		if (existingUser == null)
			throw new Exception("El usuario ingresado no existe.");

		if (existingUser.getActive())
			throw new Exception("El usuario ingresado ya se encuentra activo.");

		Interval interval = new Interval(new DateTime(existingUser.getLastEvent().getDate()), new DateTime());
		if (interval.toDuration().getStandardHours() < 24) {
			if (existingUser.getLastEvent().getToken().equals(token)) {
				existingUser.setActive(Boolean.TRUE);
				existingUser.setLoginAttempt(0);
				this.userRepository.save(existingUser);
			} else {
				throw new Exception("Ha fallado el proceso de validacion. Consulte atraves de pfipep@gmail.com");
			}
		} else {
			throw new Exception("El link de activación se ha vencido, vuelve a gestionarlo.");
		}
	}

	public void requestActive(String username) throws Exception {

		User existingUser = this.userRepository.findByUsername(username);
		if (existingUser == null)
			throw new Exception("El usuario ingresado no existe.");

		if (existingUser.getActive())
			throw new Exception("El usuario ingresado ya se encuentra activo.");

		existingUser.setLastEvent(new UserAccountEvent());
		existingUser.getLastEvent().setDate(new Date());
		existingUser.getLastEvent().setType(UserAccountEventType.REQUEST_ACTIVATION);
		existingUser.getLastEvent().setToken(UUID.randomUUID().toString());

		this.userRepository.save(existingUser);
		this.mailService.sendMail(existingUser);
	}
	
	public void requestPasswordReset(String username) throws Exception {

		User existingUser = this.userRepository.findByUsername(username);
		if (existingUser == null)
			throw new Exception("El usuario ingresado no existe.");

		if (!existingUser.getActive())
			throw new Exception("Username not active");

		existingUser.setLastEvent(new UserAccountEvent());
		existingUser.getLastEvent().setDate(new Date());
		existingUser.getLastEvent().setType(UserAccountEventType.PASSWORD_RESET);
		existingUser.getLastEvent().setToken(UUID.randomUUID().toString());

		this.userRepository.save(existingUser);
		this.mailService.sendMail(existingUser);
	}
	
	public void requestUnlock(String username) throws Exception {

		User existingUser = this.userRepository.findByUsername(username);
		if (existingUser == null)
			throw new Exception("El usuario ingresado no existe.");

		if (existingUser.getActive()) {
			throw new Exception("El usuario ingresado no esta bloqueado.");
		} else {
			if (existingUser.getLastEvent().getType() == UserAccountEventType.CREATION) {
				throw new Exception("El usuario esta pendiente de activación. Verifica tu correo electrónico.");
			}
		}

		existingUser.setLastEvent(new UserAccountEvent());
		existingUser.getLastEvent().setDate(new Date());
		existingUser.getLastEvent().setType(UserAccountEventType.UNLOCK);
		existingUser.getLastEvent().setToken(UUID.randomUUID().toString());

		this.userRepository.save(existingUser);
		this.mailService.sendMail(existingUser);
	}

	public User login(User user) throws Exception {

		User existingUser = this.userRepository.findByUsername(user.getUsername());
		if (existingUser == null)
			throw new Exception("El usuario ingresado no existe.");

		if (!existingUser.getActive()) {
			
			if (existingUser.getLastEvent().getType() == UserAccountEventType.CREATION) {
				throw new Exception("El usuario esta pendiente de activación. Verifica tu correo electrónico.");
			} else if (existingUser.getLastEvent().getType() == UserAccountEventType.LOCK) {
				throw new Exception("El usuario ingresado esta bloqueado.");
			}
		}
			
		if (!BCrypt.checkpw(user.getPassword(), existingUser.getPassword())) {
			existingUser.setLoginAttempt(existingUser.getLoginAttempt() + 1);
			if (existingUser.getLoginAttempt() > 3) {
				existingUser.setActive(Boolean.FALSE);
				existingUser.setLastEvent(new UserAccountEvent());
				existingUser.getLastEvent().setDate(new Date());
				existingUser.getLastEvent().setType(UserAccountEventType.LOCK);
				this.userRepository.save(existingUser);
				throw new Exception("La contraseña no corresponde. Usuario bloqueado!");
			} else {
				this.userRepository.save(existingUser);
				throw new Exception("La contraseña no corresponde.");
			}
		} else {
			existingUser.setLoginAttempt(0);
			existingUser.setLastEvent(null);
			return this.userRepository.save(existingUser);
		}
	}
}

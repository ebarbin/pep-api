package ar.edu.uade.pfi.pep.service;

import java.util.Date;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
			throw new Exception("Username already exist");

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());

		user.setPassword(hashedPassword);
		user.setActive(Boolean.FALSE);
		user.setLoginAttempt(Integer.valueOf(0));
		user.setLastEvent(new UserAccountEvent());
		user.getLastEvent().setDate(new Date());
		user.getLastEvent().setType(UserAccountEventType.CREATION);
		user.getLastEvent().setToken(UUID.randomUUID().toString());

		this.userRepository.save(user);
		this.mailService.sendMail("emmanuel.barbin@gmail.com", "USUARIO CREADOP",
				"HAS CREADO UN USUARIO DE MANERA IMPECA!");
	}

	public User active(User user, String token) throws Exception {

		User existingUser = this.userRepository.findByUsername(user.getUsername());
		if (existingUser == null)
			throw new Exception("Username not exist");

		if (existingUser.getActive())
			throw new Exception("Username already active");

		Interval interval = new Interval(new DateTime(existingUser.getLastEvent().getDate()), new DateTime());
		if (interval.toDuration().getStandardHours() < 24) {
			if (existingUser.getLastEvent().getToken().equals(token)) {
				existingUser.setActive(Boolean.TRUE);
				this.userRepository.save(existingUser);
			} else {
				throw new Exception("Invalid token");
			}
		} else {
			throw new Exception("Activation process was expired");
		}

		return existingUser;
	}

	public User login(User user) throws Exception {

		User existingUser = this.userRepository.findByUsername(user.getUsername());
		if (existingUser == null)
			throw new Exception("Username not exist");

		if (!existingUser.getActive())
			throw new Exception("Username is not active");

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());

		if (!existingUser.getPassword().equals(hashedPassword)) {
			existingUser.setLoginAttempt(existingUser.getLoginAttempt() + 1);
			if (existingUser.getLoginAttempt() == 3) {
				existingUser.setActive(Boolean.FALSE);
				this.userRepository.save(existingUser);
				throw new Exception("Password is not valid. User block!");
			} else {
				this.userRepository.save(existingUser);
				throw new Exception("Password is not valid");
			}
		} else {
			existingUser.setLoginAttempt(0);
			return this.userRepository.save(existingUser);
		}
	}
}

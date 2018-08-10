package ar.edu.uade.pfi.pep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.repository.UserRepository;
import ar.edu.uade.pfi.pep.repository.document.User;

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

		User newUser = this.userRepository.save(user);
		this.mailService.sendMail("emmanuel.barbin@gmail.com", "USUARIO CREADOP",
				"HAS CREADO UN USUARIO DE MANERA IMPECA!");
	}

	public User login(User user) throws Exception {

		User existingUser = this.userRepository.findByUsername(user.getUsername());
		if (existingUser == null)
			throw new Exception("Username not exist");

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());

		if (!existingUser.getPassword().equals(hashedPassword)) {
			// if (user.getLoginAttempt()
		}

		user.setPassword(hashedPassword);
		this.userRepository.save(user);

		return user;
	}
}

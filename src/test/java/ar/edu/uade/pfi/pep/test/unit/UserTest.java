package ar.edu.uade.pfi.pep.test.unit;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.uade.pfi.pep.repository.UserRepository;
import ar.edu.uade.pfi.pep.repository.document.user.User;
import ar.edu.uade.pfi.pep.repository.document.user.UserAccountEvent;
import ar.edu.uade.pfi.pep.repository.document.user.UserAccountEventType;
import ar.edu.uade.pfi.pep.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	private User activePendingUser;
	private User nonExistUser;
	private User lockUser;
	private User invalidPasswordUser;
	private User validUser;
	
	@Test
	public void nonExistentUser() throws Exception {
		try {
			this.userService.login(this.nonExistUser);
			assert(false);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("El usuario ingresado no existe."));
		}
	}
	
	@Test
	public void activationPendingUser() throws Exception {
		try {
			this.userService.login(this.activePendingUser);
			assert(false);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("El usuario esta pendiente de activación. Verifica tu correo electrónico."));
		}
	}
	
	@Test
	public void lockUser() throws Exception {
		try {
			this.userService.login(this.lockUser);
			assert(false);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("El usuario ingresado está bloqueado."));
		}
	}
	
	@Test
	public void invalidPassword() throws Exception {
		try {
			this.userService.login(this.invalidPasswordUser);
			assert(false);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("La contraseña no corresponde."));
		}
	}
	
	@Test
	public void loginOK() throws Exception {
		try {
			this.userService.login(this.validUser);
			assert(true);
		} catch (Exception e) {
			assert(false);
		}
	}
	
	@Before
	public void before() {
		this.nonExistUser = new User();
		
		this.activePendingUser = new User();
		this.activePendingUser.setActive(Boolean.FALSE);
		this.activePendingUser.setUsername("test");
		UserAccountEvent event = new UserAccountEvent();
		event.setType(UserAccountEventType.PENDING_ACTIVATION);
		this.activePendingUser.setLastEvent(event);
		this.userRepository.save(this.activePendingUser);
		
		this.lockUser = new User();
		this.lockUser.setActive(Boolean.FALSE);
		this.lockUser.setUsername("test2");
		UserAccountEvent event2 = new UserAccountEvent();
		event2.setType(UserAccountEventType.LOCK);
		this.lockUser.setLastEvent(event2);
		this.userRepository.save(this.lockUser);
		
		this.invalidPasswordUser = new User();
		this.invalidPasswordUser.setActive(Boolean.TRUE);
		this.invalidPasswordUser.setUsername("test3");
		this.invalidPasswordUser.setPassword(BCrypt.hashpw("123", BCrypt.gensalt()));
		this.userRepository.save(this.invalidPasswordUser);
		this.invalidPasswordUser.setPassword("CUALQUIER COSA");
		
		this.validUser = new User();
		this.validUser.setActive(Boolean.TRUE);
		this.validUser.setUsername("test4");
		this.validUser.setPassword(BCrypt.hashpw("123", BCrypt.gensalt()));
		this.userRepository.save(this.validUser);
		this.validUser.setPassword("123");
	}
	
	@After
	public void after() {
		this.userRepository.delete(this.activePendingUser);
		this.userRepository.delete(this.lockUser);
		this.userRepository.delete(this.invalidPasswordUser);
		this.userRepository.delete(this.validUser);
	}
}

package ar.edu.uade.pfi.pep.repository.document;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

	@Id
	private String id;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	private Boolean active;
	private int loginAttempt;
	private UserAccountEvent lastEvent;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public int getLoginAttempt() {
		return loginAttempt;
	}

	public void setLoginAttempt(int loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	public UserAccountEvent getLastEvent() {
		return lastEvent;
	}

	public void setLastEvent(UserAccountEvent lastEvent) {
		this.lastEvent = lastEvent;
	}
}
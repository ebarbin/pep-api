package ar.edu.uade.pfi.pep.repository.document;

import java.util.Date;

public class UserAccountEvent {

	private String token;
	private Date date;
	private UserAccountEventType type;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UserAccountEventType getType() {
		return type;
	}

	public void setType(UserAccountEventType type) {
		this.type = type;
	}
}

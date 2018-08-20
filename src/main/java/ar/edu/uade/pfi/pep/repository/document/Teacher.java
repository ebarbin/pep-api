package ar.edu.uade.pfi.pep.repository.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ar.edu.uade.pfi.pep.repository.document.user.User;

@Document(collection = "teacher")
public class Teacher {

	@Id
	private String id;
	private String intituteId;
	private User user;
	private String documentType;
	private String documentNumber;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIntituteId() {
		return intituteId;
	}

	public void setIntituteId(String intituteId) {
		this.intituteId = intituteId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
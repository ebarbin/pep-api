package ar.edu.uade.pfi.pep.repository.document;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "student")
public class Student {

	private String id;
	private String intituteId;
	private String userId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

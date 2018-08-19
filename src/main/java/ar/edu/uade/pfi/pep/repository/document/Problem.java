package ar.edu.uade.pfi.pep.repository.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "problem")
public class Problem {

	@Id
	private String id;
	private String name;
	private String instituteId;
	private String userId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

package ar.edu.uade.pfi.pep.common;

import org.springframework.stereotype.Component;

@Component("request")
public class RequestDataHolder {

	private String instituteId;
	private String userId;

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getInstituteId() {
		return this.instituteId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

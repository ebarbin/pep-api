package ar.edu.uade.pfi.pep.controller.response;

import org.springframework.http.HttpStatus;

/**
 * @author ebarbin
 *
 */
public class Response {

	private Object body;
	private boolean ok;
	private int status;
	private String statusText;

	private Response() {}

	public static Response ok() {
		Response response = new Response();
		response.setOk(true);
		return response;
	}
	
	public static Response ok(Object data) {
		Response response = new Response();
		response.setBody(data);
		response.setOk(true);
		return response;
	}
	
	public static Response error(HttpStatus status, String errorDescription) {
		Response response = new Response();
		response.setBody(errorDescription);
		response.setStatus(status.value());
		response.setStatusText(status.name());
		response.setOk(false);
		
		return response;
	}
	
	public static Response error(String errorDescription) {
		Response response = new Response();
		response.setBody(errorDescription);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setStatusText(HttpStatus.BAD_REQUEST.name());
		response.setOk(false);
		
		return response;
	}
	
	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
}

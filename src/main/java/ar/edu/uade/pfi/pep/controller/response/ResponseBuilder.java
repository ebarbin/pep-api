package ar.edu.uade.pfi.pep.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author ebarbin
 *
 */
public class ResponseBuilder {

	public static ResponseEntity<Response> success(Object data) {
		return ResponseEntity.ok().body(Response.ok(data));
	}
	
	public static ResponseEntity<Response> error(HttpStatus status, Exception e) {
		return ResponseEntity.status(status).body(Response.error(e.getMessage()));
	}
	
	public static ResponseEntity<Response> error(HttpStatus status, String errorDescription) {
		return ResponseEntity.status(status).body(Response.error(status, errorDescription));
	}
	
	public static ResponseEntity<Response> error(Exception e) {
		return ResponseEntity.badRequest().body(Response.error(e.getMessage()));
	}
	
	public static ResponseEntity<Response> error(String errorDescription) {
		return ResponseEntity.badRequest().body(Response.error(errorDescription));
	}
	
	public static ResponseEntity<Object> validationError(String errorDescription) {
		return ResponseEntity.badRequest().body(Response.error(errorDescription));
	}

	public static ResponseEntity<Response> success() {
		return ResponseEntity.ok().body(Response.ok());
	}
}

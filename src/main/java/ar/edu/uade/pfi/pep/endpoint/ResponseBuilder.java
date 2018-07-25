package ar.edu.uade.pfi.pep.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

	public static ResponseEntity<Response> success(Object data) {
		return ResponseEntity.ok().body(Response.build(data));
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
}

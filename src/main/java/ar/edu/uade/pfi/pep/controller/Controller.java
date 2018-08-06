package ar.edu.uade.pfi.pep.controller;

import org.springframework.http.ResponseEntity;

import ar.edu.uade.pfi.pep.controller.response.Response;

public interface Controller<T, D> {

	public ResponseEntity<Response> post(T t);

	public ResponseEntity<Response> put(D d,T t);

	public ResponseEntity<Response> findAll();

	public ResponseEntity<Response> delete(D d);
}

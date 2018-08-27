package ar.edu.uade.pfi.pep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.pfi.pep.controller.response.Response;
import ar.edu.uade.pfi.pep.controller.response.ResponseBuilder;
import ar.edu.uade.pfi.pep.repository.document.Inscription;
import ar.edu.uade.pfi.pep.service.InscriptionService;

@RestController
@RequestMapping("/inscription")
public class InscriptionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InscriptionController.class);
	
	@Autowired
	private InscriptionService service;
	
	@PostMapping
	public ResponseEntity<Response> createInscription(@RequestBody Inscription inscription) {
		try {
			return ResponseBuilder.success(this.service.createInscription(inscription));
		} catch (Exception e) {
			InscriptionController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@DeleteMapping("/{inscriptionId}")
	public ResponseEntity<Response> deleteInscription(@PathVariable("inscriptionId") String inscriptionId) {
		try {
			this.service.deleteInscription(inscriptionId);
			return ResponseBuilder.success();
		} catch (Exception e) {
			InscriptionController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@GetMapping
	public ResponseEntity<Response> getInscriptions() {
		try {
			return ResponseBuilder.success(this.service.getInscriptions());
		} catch (Exception e) {
			InscriptionController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
}

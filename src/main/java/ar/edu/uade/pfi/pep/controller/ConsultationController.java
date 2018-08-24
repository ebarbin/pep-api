package ar.edu.uade.pfi.pep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.pfi.pep.controller.response.Response;
import ar.edu.uade.pfi.pep.controller.response.ResponseBuilder;
import ar.edu.uade.pfi.pep.repository.document.consultation.Consultation;
import ar.edu.uade.pfi.pep.service.ConsultationService;

@RestController
@RequestMapping("/consultation")
public class ConsultationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultationController.class);
	
	@Autowired
	private ConsultationService consultationService;
	
	@PostMapping
	public ResponseEntity<Response> sendConsultation(@RequestBody Consultation consultation) {
		try {
			this.consultationService.sendConsultation(consultation);
			return ResponseBuilder.success();
		} catch (Exception e) {
			ConsultationController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
}

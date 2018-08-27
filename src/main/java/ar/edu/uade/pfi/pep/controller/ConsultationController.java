package ar.edu.uade.pfi.pep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping("/student")
	public ResponseEntity<Response> sendConsultation(@RequestBody Consultation consultation) {
		try {
			this.consultationService.sendConsultation(consultation);
			return ResponseBuilder.success();
		} catch (Exception e) {
			ConsultationController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/teacher")
	public ResponseEntity<Response> sendResponse(@RequestBody Consultation consultation) {
		try {
			this.consultationService.sendResponse(consultation);
			return ResponseBuilder.success();
		} catch (Exception e) {
			ConsultationController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@GetMapping("/student")
	public ResponseEntity<Response> getStudentConsultations() {
		try {
			return ResponseBuilder.success(this.consultationService.getStudentConsultations());
		} catch (Exception e) {
			ConsultationController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/teacher")
	public ResponseEntity<Response> getTeacherConsultations() {
		try {
			return ResponseBuilder.success(this.consultationService.getTeacherConsultations());
		} catch (Exception e) {
			ConsultationController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/unreaded/student")
	public ResponseEntity<Response> getStudentUnreadedResponses() {
		try {
			return ResponseBuilder.success(this.consultationService.getStudentUnreadedResponses());
		} catch (Exception e) {
			ConsultationController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/unreaded/teacher")
	public ResponseEntity<Response> getTeacherUnreadedConsultations() {
		try {
			return ResponseBuilder.success(this.consultationService.getTeacherUnreadedConsultations());
		} catch (Exception e) {
			ConsultationController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/mark-as-read/student")
	public ResponseEntity<Response> markAsReadStudentResponse(@RequestBody Consultation consultation) {
		try {
			return ResponseBuilder.success(this.consultationService.markAsReadStudentResponse(consultation));
		} catch (Exception e) {
			ConsultationController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/mark-as-read/teacher")
	public ResponseEntity<Response> markAsReadConsultation(@RequestBody Consultation consultation) {
		try {
			return ResponseBuilder.success(this.consultationService.markAsReadConsultation(consultation));
		} catch (Exception e) {
			ConsultationController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@DeleteMapping("/{consultationId}")
	public ResponseEntity<Response> deleteById(@PathVariable("consultationId") String consultationId) {
		try {
			this.consultationService.deleteById(consultationId);
			return ResponseBuilder.success();
		} catch (Exception e) {
			ConsultationController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	
}

package ar.edu.uade.pfi.pep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.pfi.pep.controller.response.Response;
import ar.edu.uade.pfi.pep.controller.response.ResponseBuilder;
import ar.edu.uade.pfi.pep.service.InscriptionService;
import ar.edu.uade.pfi.pep.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	private StudentService service;
	
	@Autowired
	private InscriptionService inscriptionService;
	
	@GetMapping()
	public ResponseEntity<Response> get() {
		try {
			return ResponseBuilder.success(this.service.getStudent());
		} catch (Exception e) {
			StudentController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	
	@GetMapping("/byCourseId/{courseId}")
	public ResponseEntity<Response> getStudentsByCourseId(@PathVariable("courseId") String courseId) {
		try {
			return ResponseBuilder.success(this.inscriptionService.getStudentsByCourseId(courseId));
		} catch (Exception e) {
			StudentController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
}

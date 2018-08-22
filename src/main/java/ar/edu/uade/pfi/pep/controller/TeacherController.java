package ar.edu.uade.pfi.pep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.pfi.pep.controller.response.Response;
import ar.edu.uade.pfi.pep.controller.response.ResponseBuilder;
import ar.edu.uade.pfi.pep.service.TeacherService;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);
	
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping()
	public ResponseEntity<Response> get() {
		try {
			return ResponseBuilder.success(this.teacherService.getTeacher());
		} catch (Exception e) {
			TeacherController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
}

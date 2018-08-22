package ar.edu.uade.pfi.pep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.pfi.pep.controller.response.Response;
import ar.edu.uade.pfi.pep.controller.response.ResponseBuilder;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping()
	public ResponseEntity<Response> get() {
		try {
			return ResponseBuilder.success(this.studentService.getStudent());
		} catch (Exception e) {
			StudentController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@PutMapping("/selected-course")
	public ResponseEntity<Response> updateSelectedCourse(@RequestBody Course course) {
		try {
			return ResponseBuilder.success(this.studentService.updateSelectedCourse(course));
		} catch (Exception e) {
			StudentController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@PutMapping("/selected-problem")
	public ResponseEntity<Response> updateSelectedProblem(@RequestBody Problem problem) {
		try {
			return ResponseBuilder.success(this.studentService.updateSelectedProblem(problem));
		} catch (Exception e) {
			StudentController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
}

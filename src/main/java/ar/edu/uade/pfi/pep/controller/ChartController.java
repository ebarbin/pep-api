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
import ar.edu.uade.pfi.pep.service.ChartService;

@RestController
@RequestMapping("/chart")
public class ChartController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChartController.class);

	@Autowired
	private ChartService chartService;

	@GetMapping("/students-per-course")
	public ResponseEntity<Response> getStudentsPerCourse() {
		try {
			return ResponseBuilder.success(this.chartService.getStudentsPerCourse());
		} catch (Exception e) {
			ChartController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/progress-student-per-course/{courseId}")
	public ResponseEntity<Response> getProgressStudentsPerCourse(@PathVariable("courseId") String courseId) {
		try {
			return ResponseBuilder.success(this.chartService.getProgressStudentsPerCourse(courseId));
		} catch (Exception e) {
			ChartController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@GetMapping("/total-progress-student-per-course/{courseId}")
	public ResponseEntity<Response> getTotalProgressStudentsPerCourse(@PathVariable("courseId") String courseId) {
		try {
			return ResponseBuilder.success(this.chartService.getTotalProgressStudentsPerCourse(courseId));
		} catch (Exception e) {
			ChartController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
}

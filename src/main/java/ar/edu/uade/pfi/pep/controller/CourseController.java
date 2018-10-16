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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.pfi.pep.controller.response.Response;
import ar.edu.uade.pfi.pep.controller.response.ResponseBuilder;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

	@Autowired
	private CourseService service;

	@PostMapping
	public ResponseEntity<Response> createCourse(@RequestBody Course course) {
		try {
			this.service.createCourse(course);
			return ResponseBuilder.success();
		} catch (Exception e) {
			CourseController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/{courseId}")
	public ResponseEntity<Response> updateCourse(@PathVariable("courseId") String courseId,
			@RequestBody Course course) {
		try {
			this.service.updateCourse(courseId, course);
			return ResponseBuilder.success();
		} catch (Exception e) {
			CourseController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/forTeacher")
	public ResponseEntity<Response> getCoursesForTeacher() {
		try {
			return ResponseBuilder.success(this.service.getCoursesForTeacher());
		} catch (Exception e) {
			CourseController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/forStudent")
	public ResponseEntity<Response> getCoursesForStudent() {
		try {
			return ResponseBuilder.success(this.service.getCoursesForStudent());
		} catch (Exception e) {
			CourseController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/{courseId}")
	public ResponseEntity<Response> findById(@PathVariable("courseId") String courseId) {
		try {
			return ResponseBuilder.success(this.service.findById(courseId));
		} catch (Exception e) {
			CourseController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@DeleteMapping("/{courseId}")
	public ResponseEntity<Response> deleteById(@PathVariable("courseId") String courseId) {
		try {
			this.service.deleteById(courseId);
			return ResponseBuilder.success();
		} catch (Exception e) {
			CourseController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@GetMapping("/forTeacher/like")
	public ResponseEntity<Response> findForTeacherByNameLike(@RequestParam("nameSearch") String nameSearch) {
		try {
			return ResponseBuilder.success(this.service.findForTeacherByNameLike(nameSearch));
		} catch (Exception e) {
			CourseController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@GetMapping("/forStudent/like")
	public ResponseEntity<Response> findForStudentByNameLike(@RequestParam("nameSearch") String nameSearch) {
		try {
			return ResponseBuilder.success(this.service.findForStudentByNameLike(nameSearch));
		} catch (Exception e) {
			CourseController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
}

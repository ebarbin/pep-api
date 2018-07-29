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
import ar.edu.uade.pfi.pep.repository.document.Recipe;
import ar.edu.uade.pfi.pep.service.CourseService;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

	@Autowired
	private CourseService service;

	@PostMapping
	public ResponseEntity<Response> post(@RequestBody Recipe recipe) {
		try {
			return ResponseBuilder.success(this.service.save(recipe));
		} catch (Exception e) {
			RecipeController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	

	@PutMapping("/{id}")
	public ResponseEntity<Response> put(@RequestBody Recipe recipe) {
		try {
			return ResponseBuilder.success(this.service.save(recipe));
		} catch (Exception e) {
			RecipeController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@GetMapping
	public ResponseEntity<Response> getAll() {
		try {
			return ResponseBuilder.success(this.service.getAll());
		} catch (Exception e) {
			RecipeController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(@PathVariable("id") Integer id) {
		try {
			this.service.delete(id);
			return ResponseBuilder.success();
		} catch (Exception e) {
			RecipeController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

}

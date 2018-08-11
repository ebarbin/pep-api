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
import ar.edu.uade.pfi.pep.service.RecipeService;

@RestController
@RequestMapping("/recipe")
public class RecipeController implements Controller<Recipe, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

	@Autowired
	private RecipeService recipeService;

	@PostMapping
	public ResponseEntity<Response> post(@RequestBody Recipe recipe) {
		try {
			return ResponseBuilder.success(this.recipeService.save(recipe));
		} catch (Exception e) {
			RecipeController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response> put(@PathVariable("id") String id, @RequestBody Recipe recipe) {
		try {
			return ResponseBuilder.success(this.recipeService.save(recipe));
		} catch (Exception e) {
			RecipeController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping
	public ResponseEntity<Response> findAll() {
		try {
			return ResponseBuilder.success(this.recipeService.findAll());
		} catch (Exception e) {
			RecipeController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(@PathVariable("id") String id) {
		try {
			this.recipeService.delete(id);
			return ResponseBuilder.success();
		} catch (Exception e) {
			RecipeController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

}

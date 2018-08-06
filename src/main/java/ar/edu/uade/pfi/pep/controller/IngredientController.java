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
import ar.edu.uade.pfi.pep.repository.document.Ingredient;
import ar.edu.uade.pfi.pep.service.IngredientService;

@RestController
@RequestMapping("/ingredient")
public class IngredientController implements Controller<Ingredient, Integer>{

	private static final Logger LOGGER = LoggerFactory.getLogger(IngredientController.class);
	
	@Autowired
	private IngredientService ingredientService;

	@PostMapping
	public ResponseEntity<Response> post(@RequestBody Ingredient ingredient) {
		try {
			return ResponseBuilder.success(this.ingredientService.save(ingredient));
		} catch (Exception e) {
			IngredientController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response> put(@PathVariable("id") Integer id, @RequestBody Ingredient ingredient) {
		try {
			return ResponseBuilder.success(this.ingredientService.save(ingredient));
		} catch (Exception e) {
			IngredientController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping
	public ResponseEntity<Response> findAll() {
		try {
			return ResponseBuilder.success(this.ingredientService.findAll());
		} catch (Exception e) {
			IngredientController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(@PathVariable("id") Integer id) {
		try {
			this.ingredientService.delete(id);
			return ResponseBuilder.success();
		} catch (Exception e) {
			IngredientController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
}

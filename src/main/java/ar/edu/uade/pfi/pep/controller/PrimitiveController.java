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
import ar.edu.uade.pfi.pep.repository.document.Primitive;
import ar.edu.uade.pfi.pep.service.PrimitiveService;

@RestController
@RequestMapping("/primitive")
public class PrimitiveController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrimitiveController.class);

	@Autowired
	private PrimitiveService service;

	@GetMapping
	public ResponseEntity<Response> getAll() {
		try {
			return ResponseBuilder.success(this.service.getPrimitives());
		} catch (Exception e) {
			PrimitiveController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/{primitiveId}")
	public ResponseEntity<Response> getById(@PathVariable("primitiveId") String primitiveId) {
		try {
			return ResponseBuilder.success(this.service.findById(primitiveId));
		} catch (Exception e) {
			PrimitiveController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PostMapping
	public ResponseEntity<Response> createPrimitive(@RequestBody Primitive primitive) {
		try {
			this.service.createPrimitive(primitive);
			return ResponseBuilder.success();
		} catch (Exception e) {
			PrimitiveController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/{primitiveId}")
	public ResponseEntity<Response> updatePrimitive(@PathVariable("primitiveId") String primitiveId,
			@RequestBody Primitive primitive) {
		try {
			this.service.updatePrimitive(primitiveId, primitive);
			return ResponseBuilder.success();
		} catch (Exception e) {
			PrimitiveController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@DeleteMapping("/{primitiveId}")
	public ResponseEntity<Response> deleteById(@PathVariable("primitiveId") String primitiveId) {
		try {
			this.service.deleteById(primitiveId);
			return ResponseBuilder.success();
		} catch (Exception e) {
			PrimitiveController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@GetMapping("/like")
	public ResponseEntity<Response> findByNameLike(@RequestParam("nameSearch") String nameSearch) {
		try {
			return ResponseBuilder.success(this.service.findByNameLike(nameSearch));
		} catch (Exception e) {
			PrimitiveController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
}

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
import ar.edu.uade.pfi.pep.repository.document.Person;
import ar.edu.uade.pfi.pep.service.ServiceExample;

@RestController
@RequestMapping("/person")
public class PersonController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private ServiceExample service;

	@PostMapping
	public ResponseEntity<Response> post(@RequestBody String name) {
		try {
			Person person = new Person(name);
			return ResponseBuilder.success(this.service.save(person));
		} catch (Exception e) {
			PersonController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response> put(@PathVariable("id") String id, @RequestBody String name) {
		try {
			Person person = new Person(name);
			person.setId(id);
			return ResponseBuilder.success(this.service.update(person));
		} catch (Exception e) {
			PersonController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(@PathVariable("id") String id) {
		try {
			this.service.deleteById(id);
			return ResponseBuilder.success();
		} catch (Exception e) {
			PersonController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping
	public ResponseEntity<Response> getAll() {
		try {
			return ResponseBuilder.success(this.service.findAll());
		} catch (Exception e) {
			PersonController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getById(@PathVariable("id") String id) {
		try {
			return ResponseBuilder.success(this.service.findById(id));
		} catch (Exception e) {
			PersonController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
}

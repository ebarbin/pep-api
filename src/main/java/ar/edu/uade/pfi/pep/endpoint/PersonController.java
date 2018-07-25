package ar.edu.uade.pfi.pep.endpoint;

import java.util.Optional;
import java.util.logging.Logger;

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

import ar.edu.uade.pfi.pep.ejemplos.Person;
import ar.edu.uade.pfi.pep.ejemplos.Prueba;
import ar.edu.uade.pfi.pep.repository.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonController {

	private final static Logger LOGGER = Logger.getLogger(PersonController.class.getName());

	@Autowired
	private PersonRepository repo;

	@Autowired
	private Prueba prueba;
	
	@PostMapping
	public Person post(@RequestBody String name) {
		PersonController.LOGGER.info(name);
		Person person = new Person(name);
		this.repo.save(person);
		return person;
	}
	
	@PutMapping("/{id}")
	public Person put(@PathVariable("id") String id, @RequestBody String name) {
		PersonController.LOGGER.info(name);
		Person person = new Person(name);
		person.setId(id);
		this.repo.save(person);
		return person;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
		PersonController.LOGGER.info(id);
		this.repo.deleteById(id);
	}

	@GetMapping
	public ResponseEntity<Response> getAll() {
		try {
			return ResponseBuilder.success(this.repo.findAll());
		} catch(Exception e) {
			PersonController.LOGGER.info(e.getMessage());
			return ResponseBuilder.error(e);
		}
	}
	
	@GetMapping("/{id}")
	public Optional<Person> getById(@PathVariable("id") String id) {
		PersonController.LOGGER.info(id);
		return this.repo.findById(id);
	}
}

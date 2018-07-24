package ar.edu.uade.pfi.pep.endpoint;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping(method = RequestMethod.POST)
	public Person post(@RequestBody String name) {
		PersonController.LOGGER.info(name);
		Person person = new Person(name);
		this.repo.save(person);
		return person;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Person put(@PathVariable("id") String id, @RequestBody String name) {
		PersonController.LOGGER.info(name);
		Person person = new Person(name);
		person.setId(id);
		this.repo.save(person);
		return person;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") String id) {
		PersonController.LOGGER.info(id);
		this.repo.deleteById(id);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Person> getAll() {
		PersonController.LOGGER.info("getAll");
		return this.repo.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Optional<Person> getById(@PathVariable("id") String id) {
		PersonController.LOGGER.info(id);
		return this.repo.findById(id);
	}
}

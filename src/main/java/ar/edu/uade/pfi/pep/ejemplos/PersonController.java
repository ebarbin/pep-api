package ar.edu.uade.pfi.pep.ejemplos;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	private final static Logger LOGGER = Logger.getLogger(PersonController.class.getName());

	@Autowired
	private PersonRepository repo;

	@Autowired
	private Prueba prueba;
	
	@RequestMapping(value = "/person/{name}", method = RequestMethod.GET)
	public Person create(@PathVariable("name") String name) {
		PersonController.LOGGER.info(name);
		Person person = new Person(name);
		this.repo.save(person);
		return person;
	}
	
	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public List<Person> getAll() {
		this.prueba.print();
		return this.repo.findAll();
	}
}

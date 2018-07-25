package ar.edu.uade.pfi.pep.ejemplos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.repository.PersonRepository;
import ar.edu.uade.pfi.pep.repository.document.Person;

@Component
public class ServiceExample {

	@Autowired
	private PersonRepository repo;

	public Optional<Person> findById(String id) {
		return this.repo.findById(id);
	}

	public List<Person> findAll() {
		return this.repo.findAll();
	}

	public void deleteById(String id) {
		this.repo.deleteById(id);
	}

	public Person update(Person person) {
		return this.repo.save(person);
	}

	public Person save(Person person) {
		return this.repo.save(person);
	}

}

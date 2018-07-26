package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import ar.edu.uade.pfi.pep.repository.document.Person;

/**
 * @author ebarbin
 *
 */
public interface PersonRepository extends MongoRepository<Person, String> {

	List<Person> findByName(@Param("name") String name);
}

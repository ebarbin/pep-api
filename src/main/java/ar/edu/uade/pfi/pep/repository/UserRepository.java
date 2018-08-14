package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.document.user.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(String username);
}

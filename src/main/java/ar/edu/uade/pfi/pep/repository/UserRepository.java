package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.repository.document.user.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(String username);
}

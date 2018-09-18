package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.custom.PrimitiveRepositoryCustom;
import ar.edu.uade.pfi.pep.repository.document.Primitive;

public interface PrimitiveRepository extends MongoRepository<Primitive, String>, PrimitiveRepositoryCustom {

	List<Primitive> findByTeacherUserId(String userId);

	Primitive findByTeacherUserIdAndName(String userId, String name);

}

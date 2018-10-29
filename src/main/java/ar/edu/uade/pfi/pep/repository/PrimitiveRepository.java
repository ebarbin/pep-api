package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.repository.custom.PrimitiveRepositoryCustom;
import ar.edu.uade.pfi.pep.repository.document.Primitive;

@Repository
public interface PrimitiveRepository extends MongoRepository<Primitive, String>, PrimitiveRepositoryCustom {

	List<Primitive> findByTeacherUserId(String userId);

	Primitive findByTeacherUserIdAndName(String userId, String name);

	List<Primitive> findByTeacherIsNull();
}

package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.custom.PrimitiveRepositoryCustom;
import ar.edu.uade.pfi.pep.repository.document.Primitive;

public interface PrimitiveRepository extends MongoRepository<Primitive, String>, PrimitiveRepositoryCustom {

}

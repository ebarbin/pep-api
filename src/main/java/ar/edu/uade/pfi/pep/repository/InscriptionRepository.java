package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import ar.edu.uade.pfi.pep.repository.document.Inscription;

public interface InscriptionRepository extends MongoRepository<Inscription, String>, QueryByExampleExecutor<Inscription> {

}

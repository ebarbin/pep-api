package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.repository.document.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

	Student findByDocumentNumberAndDocumentType(String documentNumber, String documentType);

	Student findByUserId(String userId);
}

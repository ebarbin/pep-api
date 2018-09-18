package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.document.Student;

public interface StudentRepository extends MongoRepository<Student, String> {

	Student findByDocumentNumberAndDocumentType(String documentNumber, String documentType);

	Student findByUserId(String userId);
}

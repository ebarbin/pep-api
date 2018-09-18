package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.document.Teacher;

public interface TeacherRepository extends MongoRepository<Teacher, String> {

	Teacher findByDocumentNumberAndDocumentType(String documentNumber, String documentType);

	Teacher findByUserId(String userId);
}

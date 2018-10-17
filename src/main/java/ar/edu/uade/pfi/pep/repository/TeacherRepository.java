package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.repository.document.Teacher;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {

	Teacher findByDocumentNumberAndDocumentType(String documentNumber, String documentType);

	Teacher findByUserId(String userId);
}

package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.document.Student;

public interface StudentRepository extends MongoRepository<Student, String> {

	Student findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);

	Student findByInstituteIdAndUserId(String instituteId, String userId);

	List<Student> findByInstituteIdAndCoursesId(String instituteId, String courseId);
}

package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.custom.CustomConsultationRepository;
import ar.edu.uade.pfi.pep.repository.document.consultation.Consultation;

public interface ConsultationRepository extends MongoRepository<Consultation, String>, CustomConsultationRepository {

	List<Consultation> findByStudentUserIdOrderByCreationDateDesc(String userId);

	List<Consultation> findByStudentSelectedCourseTeacherUserIdOrderByCreationDateDesc(String userId);
}

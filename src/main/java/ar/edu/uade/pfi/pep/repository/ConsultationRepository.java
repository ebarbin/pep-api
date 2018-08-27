package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import ar.edu.uade.pfi.pep.repository.custom.ConsultationRespositoryCustom;
import ar.edu.uade.pfi.pep.repository.document.consultation.Consultation;

public interface ConsultationRepository extends MongoRepository<Consultation, String>, ConsultationRespositoryCustom,
		QueryByExampleExecutor<Consultation> {

	List<Consultation> findByStudentUserIdOrderByCreationDateDesc(String userId);
}

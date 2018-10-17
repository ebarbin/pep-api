package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.repository.custom.ConsultationRespositoryCustom;
import ar.edu.uade.pfi.pep.repository.document.Consultation;

@Repository
public interface ConsultationRepository extends MongoRepository<Consultation, String>, ConsultationRespositoryCustom {

	List<Consultation> findByStudentUserIdOrderByCreationDateDesc(String userId);
}

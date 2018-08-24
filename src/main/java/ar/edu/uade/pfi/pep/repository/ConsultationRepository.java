package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.document.consultation.Consultation;

public interface ConsultationRepository extends MongoRepository<Consultation, String> {

}

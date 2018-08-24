package ar.edu.uade.pfi.pep.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.repository.ConsultationRepository;
import ar.edu.uade.pfi.pep.repository.document.consultation.Consultation;

@Component
public class ConsultationService {

	@Autowired
	private ConsultationRepository repository;
	
	public void sendConsultation(Consultation consultation) {
		consultation.setCreationDate(new Date());
		this.repository.save(consultation);
	}
	
}

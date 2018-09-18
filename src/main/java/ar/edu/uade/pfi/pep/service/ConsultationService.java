package ar.edu.uade.pfi.pep.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.repository.ConsultationRepository;
import ar.edu.uade.pfi.pep.repository.custom.ConsultationRepositoryImpl;
import ar.edu.uade.pfi.pep.repository.document.Consultation;
import ar.edu.uade.pfi.pep.repository.document.Inscription;

@Component
public class ConsultationService {

	@Autowired
	private ConsultationRepository repository;

	@Autowired
	private ConsultationRepositoryImpl customRepository;

	@Autowired
	private InscriptionService inscriptionService;

	public void sendConsultation(Consultation consultation) {
		consultation.setCreationDate(new Date());
		consultation.setWasReadedByTeacher(Boolean.FALSE);
		this.repository.save(consultation);
	}

	public List<Consultation> getStudentConsultations() {
		
		return this.customRepository.getStudentConsultations();
	}

	public List<Consultation> getTeacherConsultations() {
		
		return this.customRepository.getTeacherConsultations();
	}

	public Long getStudentUnreadedResponses() {

		return this.customRepository.getStudentUnreadedResponses();
	}

	public Long markAsReadStudentResponse(Consultation consultation) {
		
		consultation.setWasReadedByStudent(Boolean.TRUE);
		this.repository.save(consultation);
		return this.getStudentUnreadedResponses();
	}

	public Long getTeacherUnreadedConsultations() {

		return this.customRepository.getTeacherUnreadedConsultations();
	}

	public Long markAsReadConsultation(Consultation consultation) {
		consultation.setWasReadedByTeacher(Boolean.TRUE);
		this.repository.save(consultation);
		return this.getTeacherUnreadedConsultations();
	}

	public void sendResponse(Consultation consultation) {
		
		consultation.setWasReadedByStudent(Boolean.FALSE);
		if (consultation.getStudent() == null) {
			List<Inscription> inscriptions = this.inscriptionService.getInscriptionsByCourse(consultation.getCourse());
			for (Inscription i : inscriptions) {
				consultation.setId(null);
				consultation.setStudent(i.getStudent());
				this.repository.save(consultation);
			}
		} else {
			this.repository.save(consultation);
		}
	}

	public void deleteById(String consultationId) {
		Consultation c = this.repository.findById(consultationId).get();
		c.setDeleted(true);
		this.repository.save(c);
	}

}

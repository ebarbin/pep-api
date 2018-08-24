package ar.edu.uade.pfi.pep.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.ConsultationRepository;
import ar.edu.uade.pfi.pep.repository.custom.CustomConsultationRepositoryImpl;
import ar.edu.uade.pfi.pep.repository.document.consultation.Consultation;

@Component
public class ConsultationService {

	@Autowired
	private ConsultationRepository repository;
	
	@Autowired
	private CustomConsultationRepositoryImpl customRespository;
	
	@Autowired
	private RequestDataHolder requestDataHolder;
	
	public void sendConsultation(Consultation consultation) {
		consultation.setCreationDate(new Date());
		this.repository.save(consultation);
	}
	
	public List<Consultation> getStudentConsultations() {
		return this.repository.findByStudentUserIdOrderByCreationDateDesc(this.requestDataHolder.getUserId());
	}

	public Long getStudentUnreadedResponses() {
		return this.customRespository.getStudentUnreadedResponses();
	}

	public Long markAsReadStudentResponse(Consultation consultation) {
		consultation.getTeacherResponse().setWasReaded(true);
		this.repository.save(consultation);
		return this.getStudentUnreadedResponses();
	}

	public List<Consultation> getTeacherConsultations() {
		return this.repository.findByStudentSelectedCourseTeacherUserIdOrderByCreationDateDesc(this.requestDataHolder.getUserId());
	}

	public Long getTeacherUnreadedConsultations() {
		return this.customRespository.getTeacherUnreadedConsultations();
	}

	public Long markAsReadConsultation(Consultation consultation) {
		consultation.setWasReaded(true);
		this.repository.save(consultation);
		return this.getTeacherUnreadedConsultations();
	}

	public void sendResponse(Consultation consultation) {
		consultation.getTeacherResponse().setWasReaded(false);
		this.repository.save(consultation);
	}
	
}

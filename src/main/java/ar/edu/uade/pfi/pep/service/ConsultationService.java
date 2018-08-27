package ar.edu.uade.pfi.pep.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.ConsultationRepository;
import ar.edu.uade.pfi.pep.repository.custom.ConsultationRepositoryImpl;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Teacher;
import ar.edu.uade.pfi.pep.repository.document.consultation.Consultation;
import ar.edu.uade.pfi.pep.repository.document.consultation.TeacherResponse;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Component
public class ConsultationService {

	@Autowired
	private ConsultationRepository repository;

	@Autowired
	private ConsultationRepositoryImpl customRepository;
	
	@Autowired
	private RequestDataHolder requestDataHolder;
	
	public void sendConsultation(Consultation consultation) {
		consultation.setCreationDate(new Date());
		consultation.setWasReaded(Boolean.FALSE);
		this.repository.save(consultation);
	}

	public List<Consultation> getStudentConsultations() {
		return this.customRepository.getStudentConsultations();
	}

	public List<Consultation> getTeacherConsultations() {
		return this.customRepository.getTeacherConsultations();
	}

	public Long getStudentUnreadedResponses() {
		Consultation consultation = new Consultation(new Student(new User(this.requestDataHolder.getUserId())));
		consultation.setWasReaded(Boolean.TRUE);
		
		TeacherResponse tr = new TeacherResponse();
		tr.setWasReaded(Boolean.FALSE);
		
		consultation.setTeacherResponse(tr);

		Example<Consultation> example = Example.of(consultation);
		return this.repository.count(example);
	}

	public Long markAsReadStudentResponse(Consultation consultation) {
		consultation.getTeacherResponse().setWasReaded(Boolean.TRUE);
		this.repository.save(consultation);
		return this.getStudentUnreadedResponses();
	}

	public Long getTeacherUnreadedConsultations() {

		Consultation consultation = new Consultation(new Teacher(new User(this.requestDataHolder.getUserId())));
		consultation.setWasReaded(Boolean.FALSE);

		Example<Consultation> example = Example.of(consultation);
		return this.repository.count(example);
	}

	public Long markAsReadConsultation(Consultation consultation) {
		consultation.setWasReaded(Boolean.TRUE);
		this.repository.save(consultation);
		return this.getTeacherUnreadedConsultations();
	}

	public void sendResponse(Consultation consultation) {
		consultation.getTeacherResponse().setWasReaded(Boolean.FALSE);
		this.repository.save(consultation);
	}

	public void deleteById(String consultationId) {
		this.repository.deleteById(consultationId);
	}

}

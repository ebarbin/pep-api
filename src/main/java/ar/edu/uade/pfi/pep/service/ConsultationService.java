package ar.edu.uade.pfi.pep.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.ConsultationRepository;
import ar.edu.uade.pfi.pep.repository.custom.ConsultationRepositoryImpl;
import ar.edu.uade.pfi.pep.repository.document.Consultation;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Teacher;
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
		Consultation consultation = new Consultation(new Student(new User(this.requestDataHolder.getUserId())));
		consultation.setWasReadedByTeacher(Boolean.TRUE);
		consultation.setWasReadedByStudent(Boolean.FALSE);

		Example<Consultation> example = Example.of(consultation);
		return this.repository.count(example);
	}

	public Long markAsReadStudentResponse(Consultation consultation) {
		consultation.setWasReadedByStudent(Boolean.TRUE);
		this.repository.save(consultation);
		return this.getStudentUnreadedResponses();
	}

	public Long getTeacherUnreadedConsultations() {

		Consultation consultation = new Consultation(new Teacher(new User(this.requestDataHolder.getUserId())));
		consultation.setWasReadedByTeacher(Boolean.FALSE);

		Example<Consultation> example = Example.of(consultation);
		return this.repository.count(example);
	}

	public Long markAsReadConsultation(Consultation consultation) {
		consultation.setWasReadedByTeacher(Boolean.TRUE);
		this.repository.save(consultation);
		return this.getTeacherUnreadedConsultations();
	}

	public void sendResponse(Consultation consultation) {
		consultation.setWasReadedByStudent(Boolean.FALSE);
		this.repository.save(consultation);
	}

	public void deleteById(String consultationId) {
		this.repository.deleteById(consultationId);
	}

}

package ar.edu.uade.pfi.pep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.TeacherRepository;
import ar.edu.uade.pfi.pep.repository.document.Teacher;

@Component
public class TeacherService {

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private TeacherRepository teacherRepository;

	public Teacher getTeacher() {
		return this.teacherRepository.findByInstituteIdAndUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
	}

	public Teacher getTeacherByDocument(String documentType, String documentNumber) {
		return this.teacherRepository.findByDocumentTypeAndDocumentNumber(documentType, documentNumber);
	}
	
	public Teacher update(Teacher teacher) {
		return this.teacherRepository.save(teacher);
	}
}

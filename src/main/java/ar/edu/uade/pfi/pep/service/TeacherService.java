package ar.edu.uade.pfi.pep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.TeacherRepository;
import ar.edu.uade.pfi.pep.repository.document.Teacher;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Component
public class TeacherService {

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private TeacherRepository repository;

	public Teacher getTeacher() {
		Teacher teacher = new Teacher(new User(this.requestDataHolder.getUserId()));

		return this.repository.findOne(Example.of(teacher)).isPresent()
				? this.repository.findOne(Example.of(teacher)).get()
				: null;
	}

	public Teacher getTeacherByDocument(String documentType, String documentNumber) {

		Teacher teacher = new Teacher();
		teacher.setDocumentNumber(documentNumber);
		teacher.setDocumentType(documentType);

		return this.repository.findOne(Example.of(teacher)).isPresent()
				? this.repository.findOne(Example.of(teacher)).get()
				: null;
	}

	public Teacher update(Teacher teacher) {
		return this.repository.save(teacher);
	}
}

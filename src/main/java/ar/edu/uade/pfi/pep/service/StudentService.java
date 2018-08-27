package ar.edu.uade.pfi.pep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.StudentRepository;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Component
public class StudentService {

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private StudentRepository repository;

	public Student getStudent() {
		Example<Student> example = Example.of(new Student(new User(this.requestDataHolder.getUserId())));
		return this.repository.findOne(example).isPresent() ? this.repository.findOne(example).get() : null;
	}

	public Student update(Student student) {
		return this.repository.save(student);
	}

	public Student getStudentByDocument(String documentType, String documentNumber) {

		Student student = new Student();
		student.setDocumentNumber(documentNumber);
		student.setDocumentType(documentType);

		Example<Student> example = Example.of(student);
		return this.repository.findOne(example).isPresent() ? this.repository.findOne(example).get() : null;
	}
}

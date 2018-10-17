package ar.edu.uade.pfi.pep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.StudentRepository;
import ar.edu.uade.pfi.pep.repository.document.Student;

@Service
public class StudentService {

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private StudentRepository repository;

	public Student getStudent() {
		
		return this.repository.findByUserId(this.requestDataHolder.getUserId());
	}

	public Student update(Student student) {
		
		return this.repository.save(student);
	}

	public Student getStudentByDocument(String documentType, String documentNumber) {

		return this.repository.findByDocumentNumberAndDocumentType(documentNumber, documentType);
	}
}

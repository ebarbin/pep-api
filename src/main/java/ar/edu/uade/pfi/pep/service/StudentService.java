package ar.edu.uade.pfi.pep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.StudentRepository;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Student;

@Component
public class StudentService {

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private StudentRepository studentRepository;
	
	public Student getStudentByUserId() {
		return this.studentRepository.findByInstituteIdAndUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
	}

	public Student updateCourseSelection(Course course) {
		Student student = this.getStudentByUserId();
		student.setSelectedCourse(course);
		return this.studentRepository.save(student);
	}
}

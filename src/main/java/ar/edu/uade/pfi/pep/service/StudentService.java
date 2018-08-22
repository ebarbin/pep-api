package ar.edu.uade.pfi.pep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.StudentRepository;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.repository.document.Student;

@Component
public class StudentService {

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private StudentRepository studentRepository;

	public Student getStudent() {
		return this.studentRepository.findByInstituteIdAndUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
	}

	public Student updateSelectedCourse(Course course) {
		Student student = this.getStudent();

		for (Course c : student.getCourses()) {
			if (c.equals(student.getSelectedCourse())) {
				for (Problem p : c.getProblems()) {
					if (p.equals(student.getSelectedProblem())) {
						p.setSolution(student.getSelectedProblem().getSolution());
					}
				}
			}
		}

		student.setSelectedProblem(course.getProblems().get(0));
		student.setSelectedCourse(course);
		return this.studentRepository.save(student);
	}

	public Student updateSelectedProblem(Problem problem) {
		Student student = this.getStudent();

		for (Course c : student.getCourses()) {
			if (c.equals(student.getSelectedCourse())) {
				for (Problem p : c.getProblems()) {
					if (p.equals(student.getSelectedProblem())) {
						p.setSolution(student.getSelectedProblem().getSolution());
					}
				}
			}
		}

		for (Problem p : student.getSelectedCourse().getProblems()) {
			if (p.equals(student.getSelectedProblem())) {
				p.setSolution(student.getSelectedProblem().getSolution());
			}
		}

		student.setSelectedProblem(problem);
		return this.studentRepository.save(student);
	}

	public Student update(Student student) {
		return this.studentRepository.save(student);
	}

	public List<Student> getStudentsByCourseId(String courseId) {
		return this.studentRepository.findByInstituteIdAndCoursesId(this.requestDataHolder.getInstituteId(), courseId);
	}

	public Student getStudentByDocument(String documentType, String documentNumber) {
		return this.studentRepository.findByDocumentTypeAndDocumentNumber(documentType, documentNumber);
	}
}

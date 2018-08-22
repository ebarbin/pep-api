package ar.edu.uade.pfi.pep.service;

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
	
	public Student getStudentByUserId() {
		return this.studentRepository.findByInstituteIdAndUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
	}

	public Student updateSelectedCourse(Course course) {
		Student student = this.getStudentByUserId();
		
		for (Course c : student.getCourses()) {
			if(c.equals(student.getSelectedCourse())) {
				for(Problem p: c.getProblems()) {
					if (p.equals(student.getSelectedProblem())) {
						p.setSolution(student.getSelectedProblem().getSolution());
					}
				}
			}
		}
		
		student.setSelectedProblem(null);
		student.setSelectedCourse(course);
		return this.studentRepository.save(student);
	}

	public Student updateSelectedProblem(Problem problem) {
		Student student = this.getStudentByUserId();
		
		for (Course c : student.getCourses()) {
			if(c.equals(student.getSelectedCourse())) {
				for (Problem p: c.getProblems()) {
					if (p.equals(student.getSelectedProblem())) {
						p.setSolution(student.getSelectedProblem().getSolution());
					}
				}
			}
		}
		
		
		for (Problem p : student.getSelectedCourse().getProblems()) {
			if(p.equals(student.getSelectedProblem())) {
				p.setSolution(student.getSelectedProblem().getSolution());
			}
		}
		
		student.setSelectedProblem(problem);
		return this.studentRepository.save(student);
	}
}

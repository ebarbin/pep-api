package ar.edu.uade.pfi.pep.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.InscriptionRepository;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Inscription;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Component
public class InscriptionService {

	@Autowired
	private InscriptionRepository repository;

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private StudentService studentService;

	public Inscription createInscription(Inscription inscription) {
		Student student = this.studentService.getStudent();
		inscription.setInscriptionDate(new Date());
		inscription.setStudent(student);
		inscription = this.repository.save(inscription);

		this.workspaceService.createWorkspaceByInscription(inscription);

		return inscription;
	}

	public void deleteInscription(String inscriptionId) {
		Inscription inscription = this.repository.findById(inscriptionId).get();
		this.workspaceService.deleteByStudentAndCourse(inscription.getStudent(), inscription.getCourse());
		this.repository.deleteById(inscriptionId);
	}

	public List<Inscription> getInscriptions() {

		Example<Inscription> example = Example.of(new Inscription(new User(this.requestDataHolder.getUserId())));
		return this.repository.findAll(example);
	}

	public boolean hasInscriptionsWithCourseId(String courseId) {
		Example<Inscription> example = Example.of(new Inscription(new Course(courseId)));
		return this.repository.count(example) > 0;
	}

	public void updateInscriptionsByProblem(Problem updatedProblem) {
		List<Inscription> inscriptions = this.repository.findByCourseProblemsId(updatedProblem.getId());
		for (Inscription i : inscriptions) {
			for (Problem p : i.getCourse().getProblems()) {
				if (p.equals(updatedProblem)) {
					p.setExplanation(updatedProblem.getExplanation());
					p.setName(updatedProblem.getName());
					p.setPosExecution(updatedProblem.getPosExecution());
					p.setPreExecution(updatedProblem.getPreExecution());
					p.setPrimitives(updatedProblem.getPrimitives());
					p.setTeacher(updatedProblem.getTeacher());

					this.repository.save(i);
				}
			}
		}
	}

	public void updateInscriptionsByCourse(Course course) {
		List<Inscription> inscriptions = this.repository.findByCourseId(course.getId());
		for (Inscription i : inscriptions) {
			i.setCourse(course);
			this.repository.save(i);
		}
	}
}

package ar.edu.uade.pfi.pep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.ProblemRepository;
import ar.edu.uade.pfi.pep.repository.custom.ProblemRepositoryImpl;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Teacher;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Component
public class ProblemService {

	@Autowired
	private ProblemRepository repository;

	@Autowired
	private ProblemRepositoryImpl customRepository;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CourseService courseService;

	public List<Problem> findAll() {

		Problem problem = new Problem(new Teacher(new User(this.requestDataHolder.getUserId())));
		return this.repository.findAll(Example.of(problem));
	}

	public List<Problem> findByNameLike(String nameSearch) {
		return this.customRepository.findByNameLike(nameSearch);
	}

	public void createProblem(Problem problem) {
		Teacher teacher = this.teacherService.getTeacher();
		problem.setTeacher(teacher);
		problem.setInstituteId(this.requestDataHolder.getInstituteId());

		this.repository.save(problem);
	}

	public void updateProblem(String problemId, Problem problem) {
		Teacher teacher = this.teacherService.getTeacher();
		problem.setTeacher(teacher);
		problem.setInstituteId(this.requestDataHolder.getInstituteId());

		this.repository.save(problem);
	}

	public List<Problem> deleteById(String problemId) throws Exception {
		boolean hasCoursesByProblemId = this.courseService.hasCoursesByProblemId(problemId);
		if (hasCoursesByProblemId)
			throw new Exception("No se pude eliminar el ejercicio pues forma parte de uno o más cursos.");

		this.repository.deleteById(problemId);

		return this.findAll();
	}

	public Problem findById(String problemId) {
		return this.repository.findById(problemId).get();
	}

	public Student updateSolutionProblem(String problemId, Problem problem) {
		Student student = this.studentService.getStudent();

		student.getSelectedProblem().setSolution(problem.getSolution());
		return this.studentService.update(student);
	}
}

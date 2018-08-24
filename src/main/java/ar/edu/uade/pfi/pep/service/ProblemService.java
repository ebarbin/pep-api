package ar.edu.uade.pfi.pep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.ProblemRepository;
import ar.edu.uade.pfi.pep.repository.custom.CustomProblemRepositoryImpl;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Teacher;

@Component
public class ProblemService {

	@Autowired
	private ProblemRepository repository;

	@Autowired
	private CustomProblemRepositoryImpl customRepository;
	
	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CourseService courseService;

	public List<Problem> findAll() {

		return this.repository.findByInstituteIdAndTeacherUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
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
		List<Course> courses = this.courseService.getCoursesByProblemId(problemId);
		if (!courses.isEmpty())
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

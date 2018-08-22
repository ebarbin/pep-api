package ar.edu.uade.pfi.pep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.CourseRepository;
import ar.edu.uade.pfi.pep.repository.ProblemRepository;
import ar.edu.uade.pfi.pep.repository.StudentRepository;
import ar.edu.uade.pfi.pep.repository.TeacherRepository;
import ar.edu.uade.pfi.pep.repository.custom.CustomProblemRepositoryImpl;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Teacher;

@Component
public class ProblemService {

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private CustomProblemRepositoryImpl customProblemRepository;

	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private StudentRepository studentRepository;

	public List<Problem> findAll() {

		return this.problemRepository.findByInstituteIdAndTeacherUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
	}

	public List<Problem> findByNameLike(String nameSearch) {
		return this.customProblemRepository.findByNameLike(nameSearch);
	}

	public void createProblem(Problem problem) {
		Teacher teacher = this.teacherRepository.findByUserId(this.requestDataHolder.getUserId());
		problem.setTeacher(teacher);
		problem.setInstituteId(this.requestDataHolder.getInstituteId());

		this.problemRepository.save(problem);
	}

	public void updateProblem(String problemId, Problem problem) {
		Teacher teacher = this.teacherRepository.findByUserId(this.requestDataHolder.getUserId());
		problem.setTeacher(teacher);
		problem.setInstituteId(this.requestDataHolder.getInstituteId());

		this.problemRepository.save(problem);
	}

	public List<Problem> deleteById(String problemId) throws Exception {
		List<Course> courses = this.courseRepository
				.findByInstituteIdAndProblemsId(this.requestDataHolder.getInstituteId(), problemId);

		if (!courses.isEmpty())
			throw new Exception("No se pude eliminar el ejercicio pues forma parte de uno o más cursos.");

		this.problemRepository.deleteById(problemId);

		return this.findAll();
	}

	public Problem findById(String problemId) {
		return this.problemRepository.findById(problemId).get();
	}

	public Student updateSolutionProblem(String problemId, Problem problem) {
		Student student = this.studentRepository.findByInstituteIdAndUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
		
		student.getSelectedProblem().setSolution(problem.getSolution());
		return this.studentRepository.save(student);
	}
}

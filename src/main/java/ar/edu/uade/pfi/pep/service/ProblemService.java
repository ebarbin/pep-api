package ar.edu.uade.pfi.pep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.ProblemRepository;
import ar.edu.uade.pfi.pep.repository.custom.ProblemRepositoryImpl;
import ar.edu.uade.pfi.pep.repository.document.Primitive;
import ar.edu.uade.pfi.pep.repository.document.Problem;
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

		this.repository.save(problem);
	}

	public void updateProblem(String problemId, Problem problem) {
		Teacher teacher = this.teacherService.getTeacher();
		problem.setTeacher(teacher);
		this.repository.save(problem);
		
		this.courseService.updateCoursesByProblem(problem);
	}

	public void deleteById(String problemId) throws Exception {
		if (this.courseService.hasCoursesByProblemId(problemId))
			throw new Exception("No se pude eliminar el ejercicio pues forma parte de uno o más cursos.");

		this.repository.deleteById(problemId);
	}

	public Problem findById(String problemId) {
		return this.repository.findById(problemId).get();
	}

	public boolean hasProblemsWithPrimitiveId(String primitiveId) {
		return !this.repository.findByPrimitivesId(primitiveId).isEmpty();
	}

	public void updateProblemsByPrimitive(Primitive updatedPrimitive) {
		List<Problem> problems = this.repository.findByPrimitivesId(updatedPrimitive.getId());
		for (Problem pro : problems) {
			for (Primitive pri : pro.getPrimitives()) {
				if (pri.equals(updatedPrimitive)) {
					pri.setTeacher(updatedPrimitive.getTeacher());
					pri.setCode(updatedPrimitive.getCode());
					pri.setDescription(updatedPrimitive.getDescription());
					pri.setName(updatedPrimitive.getName());
					this.repository.save(pro);
					
					this.courseService.updateCoursesByProblem(pro);
				}
			}
		}
	}
}

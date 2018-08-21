package ar.edu.uade.pfi.pep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.ProblemRepository;
import ar.edu.uade.pfi.pep.repository.TeacherRepository;
import ar.edu.uade.pfi.pep.repository.custom.CustomProblemRepositoryImpl;
import ar.edu.uade.pfi.pep.repository.document.Problem;
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
}

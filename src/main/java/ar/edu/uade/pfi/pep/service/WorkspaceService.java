package ar.edu.uade.pfi.pep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.WorkspaceRepository;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Inscription;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Workspace;
import ar.edu.uade.pfi.pep.repository.document.WorkspaceProblem;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Component
public class WorkspaceService {

	@Autowired
	private WorkspaceRepository repository;
	
	@Autowired
	private RequestDataHolder requestDataHolder;
	
	public Workspace getActiveWorkspace() {
		Example<Workspace> example = Example.of(new Workspace(new User(this.requestDataHolder.getUserId()), true));
		return this.repository.findOne(example).isPresent() ? this.repository.findOne(example).get() : null;
	}
	
	public Workspace removeBy(Workspace workspace) {
		return this.repository.save(workspace);
	}

	public void deleteByStudentAndCourse(Student student, Course course) {
		this.repository.deleteByStudentIdAndCourseId(student.getId(), course.getId());
	}

	public Workspace updateActive(Workspace workspace) {
		
		Example<Workspace> nextActiveExample = Example.of(new Workspace(new User(this.requestDataHolder.getUserId()), workspace.getCourse(), false));
		Optional<Workspace> nextActiveOptional = this.repository.findOne(nextActiveExample);
		
		Example<Workspace> lastActiveExample = Example.of(new Workspace(new User(this.requestDataHolder.getUserId()), true));
		Optional<Workspace> lastActiveOptional = this.repository.findOne(lastActiveExample);
		if (lastActiveOptional.isPresent()) {
			Workspace lastActive = lastActiveOptional.get();
			lastActive.setActive(false);
			this.repository.save(lastActive);
		}
		
		if (nextActiveOptional.isPresent()) {
			Workspace nextActive = nextActiveOptional.get();
			nextActive.setActive(true);
			return this.repository.save(nextActive);
		}
		
		return null;
	}

	public void createWorkspaceByInscription(Inscription inscription) {
		
		Workspace worskpace = new Workspace();
		worskpace.setCourse(inscription.getCourse());
		worskpace.setStudent(inscription.getStudent());
		List<WorkspaceProblem>problems = new ArrayList<WorkspaceProblem>();
		for(Problem p : inscription.getCourse().getProblems()) {
			problems.add(new WorkspaceProblem(p));
		}
		problems.get(0).setActive(true); //activo por default el primer problema.
		worskpace.setProblems(problems);

		this.repository.save(worskpace);
	}

	public void activeOtherProblem(String workspaceId, WorkspaceProblem workspaceProblem) {
		Workspace w = this.repository.findById(workspaceId).get();
		for(WorkspaceProblem wp : w.getProblems()) {
			wp.setActive(false);
			if (wp.getProblem().getId().equals(workspaceProblem.getProblem().getId())) {
				wp.setActive(true);
			}
		}
		this.repository.save(w);
	}

	public void updateSolution(String workspaceId, WorkspaceProblem workspaceProblem) {
		Workspace w = this.repository.findById(workspaceId).get();
		for(WorkspaceProblem wp : w.getProblems()) {
			if (wp.getProblem().getId().equals(workspaceProblem.getProblem().getId())) {
				wp.setSolution(workspaceProblem.getSolution());
				break;
			}
		}
		this.repository.save(w);
	}
}

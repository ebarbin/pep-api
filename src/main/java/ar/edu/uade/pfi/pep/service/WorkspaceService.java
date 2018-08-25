package ar.edu.uade.pfi.pep.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.WorkspaceRepository;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Workspace;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Component
public class WorkspaceService {

	@Autowired
	private WorkspaceRepository repository;
	
	@Autowired
	private RequestDataHolder requestDataHolder;
	
	public Workspace createWorkspace(Workspace workspace) {
		
		Example<Workspace> example = Example.of(new Workspace(workspace.getStudent(), workspace.getCourse(),
				workspace.getCourse().getProblems().get(0), true));
		
		if (this.repository.findOne(example).isPresent()) {
			workspace.setActive(false);
		} else {
			workspace.setActive(false);
		}
		
		return this.repository.save(workspace);
	}
	
	public Workspace getActiveWorkspace() {
		Example<Workspace> example = Example.of(new Workspace(new User(this.requestDataHolder.getUserId()), true));
		return this.repository.findOne(example).isPresent() ? this.repository.findOne(example).get() : null;
	}
	
	public Workspace removeBy(Workspace workspace) {
		return this.repository.save(workspace);
	}

	public void deleteByStudentAndCourse(Student student, Course course) {
		this.repository.deleteByStudentAndCourse(student, course);
	}

	public void activeWorkspaceByCourse(Workspace workspace) {
		
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
			this.repository.save(nextActive);
		}
	}
}

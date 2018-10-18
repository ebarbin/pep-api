package ar.edu.uade.pfi.pep.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.pfi.pep.common.ProblemState;
import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.WorkspaceRepository;
import ar.edu.uade.pfi.pep.repository.document.Correction;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Inscription;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Workspace;
import ar.edu.uade.pfi.pep.repository.document.WorkspaceProblem;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Service
public class WorkspaceService {

	@Autowired
	private WorkspaceRepository repository;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;

	public Workspace getActiveWorkspace() {
		
		return this.repository.findByStudentUserIdAndActive(this.requestDataHolder.getUserId(), true);
	}

	public Workspace removeBy(Workspace workspace) {
		return this.repository.save(workspace);
	}

	public void deleteByStudentAndCourse(Student student, Course course) {
		
		this.repository.deleteByStudentIdAndCourseId(student.getId(), course.getId());
	}

	public Workspace updateActive(Workspace workspace) {
		
		List<Workspace> studentWorkspaces = this.repository.findByStudentUserId(this.requestDataHolder.getUserId());

		boolean mustActive = workspace.getCourse().getId() != null;

		Workspace wsToActive = null;
		Workspace wsToDeactive = null;

		for (Workspace w : studentWorkspaces) {
			if (wsToActive == null && mustActive && workspace.getCourse().getId().equals(w.getCourse().getId()))
				wsToActive = w;
			if (w.isActive())
				wsToDeactive = w;
		}

		if (wsToDeactive != null) {
			wsToDeactive.setActive(false);
			this.repository.save(wsToDeactive);
		}

		if (wsToActive != null) {
			wsToActive.setActive(true);
			return this.repository.save(wsToActive);
		}

		return null;
	}

	public void createWorkspaceByInscription(Inscription inscription) {

		Workspace worskpace = new Workspace();
		worskpace.setCourse(inscription.getCourse());
		worskpace.setStudent(inscription.getStudent());
		List<WorkspaceProblem> problems = new ArrayList<WorkspaceProblem>();
		for (Problem p : inscription.getCourse().getProblems()) {
			problems.add(new WorkspaceProblem(p));
		}
		problems.get(0).setActive(true); // activo por default el primer problema.
		worskpace.setProblems(problems);

		this.repository.save(worskpace);
	}

	public void activeOtherProblem(String workspaceId, WorkspaceProblem workspaceProblem) {
		Workspace w = this.repository.findById(workspaceId).get();
		for (WorkspaceProblem wp : w.getProblems()) {
			wp.setActive(false);
			if (wp.getProblem().equals(workspaceProblem.getProblem())) {
				wp.setActive(true);
			}
		}
		this.repository.save(w);
	}

	public void updateSolution(String workspaceId, WorkspaceProblem workspaceProblem) {
		Workspace w = this.repository.findById(workspaceId).get();
		for (WorkspaceProblem wp : w.getProblems()) {
			if (wp.getProblem().equals(workspaceProblem.getProblem())) {
				wp.setState(null);
				wp.setSolution(workspaceProblem.getSolution());
				break;
			}
		}
		this.repository.save(w);
	}

	public void updateWorkspacesByProblem(Problem updatedProblem) {
		List<Workspace> workspaces = this.repository.findByProblemsProblemId(updatedProblem.getId());
		for (Workspace ws : workspaces) {
			for (WorkspaceProblem wp : ws.getProblems()) {
				if (wp.getProblem().equals(updatedProblem)) {
					wp.getProblem().setExplanation(updatedProblem.getExplanation());
					wp.getProblem().setName(updatedProblem.getName());
					wp.getProblem().setPosExecution(updatedProblem.getPosExecution());
					wp.getProblem().setPreExecution(updatedProblem.getPreExecution());
					wp.getProblem().setPrimitives(updatedProblem.getPrimitives());
					wp.getProblem().setTeacher(updatedProblem.getTeacher());
					this.repository.save(ws);
				}
			}
		}
	}

	public void updateWorkspacesByCourse(Course course) {
		List<Workspace> workspaces = this.repository.findByCourseId(course.getId());
		for (Workspace ws : workspaces) {
			ws.setCourse(course);
			for (Problem p : course.getProblems()) {
				if (ws.containsProblem(p)) {
					ws.updateProblem(p);
				} else {
					ws.getProblems().add(new WorkspaceProblem(p));
				}
			}

			List<WorkspaceProblem> wpToDelete = new ArrayList<WorkspaceProblem>();
			boolean wasActive = false;
			for (WorkspaceProblem wp : ws.getProblems()) {
				if (!course.getProblems().contains(wp.getProblem())) {
					if (wp.isActive())
						wasActive = true;
					wpToDelete.add(wp);
				}
			}

			ws.getProblems().removeAll(wpToDelete);
			if (wasActive)
				ws.getProblems().get(0).setActive(true);

			this.repository.save(ws);
		}
	}

	public void markProblemAsOk(String workspaceId, WorkspaceProblem workspaceProblem) {
		Workspace w = this.repository.findById(workspaceId).get();
		for (WorkspaceProblem wp : w.getProblems()) {
			if (wp.getProblem().equals(workspaceProblem.getProblem())) {
				wp.setState("OK");
				break;
			}
		}
		this.repository.save(w);
	}

	public void markProblemAsNoOk(String workspaceId, WorkspaceProblem workspaceProblem) {
		Workspace w = this.repository.findById(workspaceId).get();
		for (WorkspaceProblem wp : w.getProblems()) {
			if (wp.getProblem().equals(workspaceProblem.getProblem())) {
				wp.setState("NOOK");
				break;
			}
		}
		this.repository.save(w);
	}

	public void markProblemAsFeedback(String workspaceId, WorkspaceProblem workspaceProblem) {
		Workspace w = this.repository.findById(workspaceId).get();
		for (WorkspaceProblem wp : w.getProblems()) {
			if (wp.getProblem().equals(workspaceProblem.getProblem())) {
				wp.setState("FEEDBACK");
				break;
			}
		}
		this.repository.save(w);
		this.mailService.sendNeedCorrectionMail(w);
	}

	public List<Workspace> getWorkspacesByCourse(String courseId) {
		return this.repository.findByCourseId(courseId);
	}

	public List<Correction> getCorrections() {

		Map<String, User> mapOfUsers = new HashMap<String, User>();
		List<Correction> results = new ArrayList<Correction>();

		List<Workspace> workspaces = this.repository.findByCourseTeacherUserId(this.requestDataHolder.getUserId());
		Correction result;
		for (Workspace ws : workspaces) {
			for (WorkspaceProblem wsp : ws.getProblems()) {
				if (ProblemState.FEEDBACK.name().equals(wsp.getState())) {
					result = new Correction();
					result.setId(wsp.getProblem().getId() + "-" + ws.getStudent().getId());
					result.setWorkspaceId(ws.getId());
					result.setCourse(ws.getCourse());
					result.setStudent(ws.getStudent());
					result.setTeacher(ws.getCourse().getTeacher());
					result.setWorkspaceProblem(wsp);
					if (!mapOfUsers.containsKey(ws.getStudent().getUser().getId())) {
						mapOfUsers.put(ws.getStudent().getUser().getId(),
								this.userService.getUser(ws.getStudent().getUser().getId()));
					}
					result.getStudent().setUser(mapOfUsers.get(ws.getStudent().getUser().getId()));
					results.add(result);
				}
			}
		}

		return results;
	}

	public void saveCorrection(Correction correction) {
		Optional<Workspace> optional = this.repository.findById(correction.getWorkspaceId());
		if (optional.isPresent()) {
			Workspace ws = optional.get();
			for (WorkspaceProblem wsp : ws.getProblems()) {
				if (wsp.getProblem().getId().equals(correction.getWorkspaceProblem().getProblem().getId())) {
					wsp.setFeedback(correction.getWorkspaceProblem().getFeedback());
					wsp.setSolution(correction.getWorkspaceProblem().getSolution());
					wsp.setState(correction.getWorkspaceProblem().getState());
				}
			}
			this.repository.save(ws);
			this.mailService.sendCorrectionMail(ws);
		}
	}
}

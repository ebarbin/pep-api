package ar.edu.uade.pfi.pep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.pfi.pep.controller.response.Response;
import ar.edu.uade.pfi.pep.controller.response.ResponseBuilder;
import ar.edu.uade.pfi.pep.repository.document.Workspace;
import ar.edu.uade.pfi.pep.repository.document.WorkspaceProblem;
import ar.edu.uade.pfi.pep.service.WorkspaceService;

@RestController
@RequestMapping("/workspace")
public class WorkspaceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkspaceController.class);

	@Autowired
	private WorkspaceService service;

	@GetMapping("/active")
	public ResponseEntity<Response> getActiveWorkspace() {
		try {
			return ResponseBuilder.success(this.service.getActiveWorkspace());
		} catch (Exception e) {
			WorkspaceController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/updateActive")
	public ResponseEntity<Response> updateActive(@RequestBody Workspace workspace) {
		try {
			return ResponseBuilder.success(this.service.updateActive(workspace));
		} catch (Exception e) {
			WorkspaceController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/active-other-problem/{workspaceId}")
	public ResponseEntity<Response> activeOtherProblem(@PathVariable("workspaceId") String workspaceId,
			@RequestBody WorkspaceProblem workspaceProblem) {
		try {
			this.service.activeOtherProblem(workspaceId, workspaceProblem);
			return ResponseBuilder.success();
		} catch (Exception e) {
			WorkspaceController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@PutMapping("/update-solution/{workspaceId}")
	public ResponseEntity<Response> updateSolution(@PathVariable("workspaceId") String workspaceId,
			@RequestBody WorkspaceProblem workspaceProblem) {
		try {
			this.service.updateSolution(workspaceId, workspaceProblem);
			return ResponseBuilder.success();
		} catch (Exception e) {
			WorkspaceController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@PutMapping("/mark-problem-ok/{workspaceId}")
	public ResponseEntity<Response> markProblemAsOk(@PathVariable("workspaceId") String workspaceId,
			@RequestBody WorkspaceProblem workspaceProblem) {
		try {
			this.service.markProblemAsOk(workspaceId, workspaceProblem);
			return ResponseBuilder.success();
		} catch (Exception e) {
			WorkspaceController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@PutMapping("/mark-problem-feedback/{workspaceId}")
	public ResponseEntity<Response> markProblemAsFeedback(@PathVariable("workspaceId") String workspaceId,
			@RequestBody WorkspaceProblem workspaceProblem) {
		try {
			this.service.markProblemAsFeedback(workspaceId, workspaceProblem);
			return ResponseBuilder.success();
		} catch (Exception e) {
			WorkspaceController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@PutMapping("/mark-problem-nook/{workspaceId}")
	public ResponseEntity<Response> markProblemAsNoOk(@PathVariable("workspaceId") String workspaceId,
			@RequestBody WorkspaceProblem workspaceProblem) {
		try {
			this.service.markProblemAsNoOk(workspaceId, workspaceProblem);
			return ResponseBuilder.success();
		} catch (Exception e) {
			WorkspaceController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
}

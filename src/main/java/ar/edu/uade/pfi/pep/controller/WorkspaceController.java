package ar.edu.uade.pfi.pep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.pfi.pep.controller.response.Response;
import ar.edu.uade.pfi.pep.controller.response.ResponseBuilder;
import ar.edu.uade.pfi.pep.repository.document.Workspace;
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
	public ResponseEntity<Response> activeWorkspaceByCourse(@RequestBody Workspace workspace) {
		try {
			this.service.activeWorkspaceByCourse(workspace);
			return ResponseBuilder.success();
		} catch (Exception e) {
			WorkspaceController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	
}

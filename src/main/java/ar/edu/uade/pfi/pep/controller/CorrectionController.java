package ar.edu.uade.pfi.pep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.pfi.pep.controller.response.Response;
import ar.edu.uade.pfi.pep.controller.response.ResponseBuilder;
import ar.edu.uade.pfi.pep.repository.document.Correction;
import ar.edu.uade.pfi.pep.service.WorkspaceService;

@RestController
@RequestMapping("/correction")
public class CorrectionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CorrectionController.class);
	
	@Autowired
	private WorkspaceService workspaceService;
	
	@GetMapping
	public ResponseEntity<Response> getCorrections() {
		try {
			return ResponseBuilder.success(this.workspaceService.getCorrections());
		} catch (Exception e) {
			CorrectionController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@PostMapping
	public ResponseEntity<Response> sendCorrection(@RequestBody Correction correction) {
		try {
			this.workspaceService.saveCorrection(correction);
			return ResponseBuilder.success();
		} catch (Exception e) {
			CorrectionController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
}

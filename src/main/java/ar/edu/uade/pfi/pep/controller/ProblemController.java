package ar.edu.uade.pfi.pep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.pfi.pep.controller.response.Response;
import ar.edu.uade.pfi.pep.controller.response.ResponseBuilder;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.service.ProblemService;

@RestController
@RequestMapping("/problem")
public class ProblemController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProblemController.class);

	@Autowired
	private ProblemService problemService;

	@GetMapping("/like")
	public ResponseEntity<Response> findByNameLike(@RequestParam("nameSearch") String nameSearch) {
		try {
			return ResponseBuilder.success(this.problemService.findByNameLike(nameSearch));
		} catch (Exception e) {
			ProblemController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping
	public ResponseEntity<Response> findAll() {
		try {
			return ResponseBuilder.success(this.problemService.findAll());
		} catch (Exception e) {
			ProblemController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/{problemId}")
	public ResponseEntity<Response> findById(@PathVariable("problemId") String problemId) {
		try {
			return ResponseBuilder.success(this.problemService.findById(problemId));
		} catch (Exception e) {
			ProblemController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@PostMapping
	public ResponseEntity<Response> createProblem(@RequestBody Problem problem) {
		try {
			this.problemService.createProblem(problem);
			return ResponseBuilder.success();
		} catch (Exception e) {
			ProblemController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/{problemId}")
	public ResponseEntity<Response> updateProblem(@PathVariable("problemId") String problemId,
			@RequestBody Problem problem) {
		try {
			this.problemService.updateProblem(problemId, problem);
			return ResponseBuilder.success();
		} catch (Exception e) {
			ProblemController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@PutMapping("/update-solution/{problemId}")
	public ResponseEntity<Response> updateSolutionProblem(@PathVariable("problemId") String problemId,
			@RequestBody Problem problem) {
		try {
			this.problemService.updateSolutionProblem(problemId, problem);
			return ResponseBuilder.success();
		} catch (Exception e) {
			ProblemController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@DeleteMapping("/{problemId}")
	public ResponseEntity<Response> deleteById(@PathVariable("problemId") String problemId) {
		try {
			return ResponseBuilder.success(this.problemService.deleteById(problemId));
		} catch (Exception e) {
			ProblemController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
}

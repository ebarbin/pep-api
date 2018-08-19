package ar.edu.uade.pfi.pep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.repository.ProblemRepository;
import ar.edu.uade.pfi.pep.repository.document.Problem;

@Component
public class ProblemService {

	@Autowired
	private ProblemRepository problemRepository;
	
	public List<Problem> getAll() {
		return this.problemRepository.findAll();
	}
}

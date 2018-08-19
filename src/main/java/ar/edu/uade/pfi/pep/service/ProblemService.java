package ar.edu.uade.pfi.pep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.ProblemRepository;
import ar.edu.uade.pfi.pep.repository.document.Problem;

@Component
public class ProblemService {

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private RequestDataHolder requestDataHolder;

	public List<Problem> getAll() {

		return this.problemRepository.findByInstituteIdAndUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
	}
}

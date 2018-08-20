package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import ar.edu.uade.pfi.pep.repository.document.Problem;

public interface CustomProblemRepository {
	List<Problem>findByNameLike(String nameSearch);
}

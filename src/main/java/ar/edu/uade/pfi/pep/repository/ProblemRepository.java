package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.document.Problem;

public interface ProblemRepository extends MongoRepository<Problem, String>, CustomProblemRepository {

	List<Problem> findByInstituteIdAndTeacherUserId(String instituteId, String userId);
}
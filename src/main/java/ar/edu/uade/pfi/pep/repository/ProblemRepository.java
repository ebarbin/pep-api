package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import ar.edu.uade.pfi.pep.repository.custom.ProblemRepositoryCustom;
import ar.edu.uade.pfi.pep.repository.document.Problem;

public interface ProblemRepository extends MongoRepository<Problem, String>, QueryByExampleExecutor<Problem>, ProblemRepositoryCustom {

	List<Problem> findByTeacherUserIdAndPrimitivesId(String userId, String primitiveId);
}

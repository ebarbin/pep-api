package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.repository.custom.ProblemRepositoryCustom;
import ar.edu.uade.pfi.pep.repository.document.Problem;

@Repository
public interface ProblemRepository extends MongoRepository<Problem, String>, ProblemRepositoryCustom {

	List<Problem> findByPrimitivesId(String primitiveId);

	Problem findByTeacherUserIdAndName(String userId, String name);

	List<Problem> findByTeacherIsNull();
}

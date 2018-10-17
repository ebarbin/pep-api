package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.repository.document.Inscription;

@Repository
public interface InscriptionRepository
		extends MongoRepository<Inscription, String> {

	List<Inscription> findByCourseProblemsId(String problemId);

	List<Inscription> findByCourseId(String courseId);

	List<Inscription> findByCourseTeacherUserId(String userId);

	List<Inscription> findByStudentUserId(String userId);

	int countByCourseId(String courseId);
}

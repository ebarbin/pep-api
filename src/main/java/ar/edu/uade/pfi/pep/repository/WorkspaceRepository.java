package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.edu.uade.pfi.pep.repository.document.Workspace;

@Repository
public interface WorkspaceRepository extends MongoRepository<Workspace, String> {

	void deleteByStudentIdAndCourseId(String studentId, String courseId);

	List<Workspace> findByStudentUserId(String userId);

	List<Workspace> findByProblemsProblemId(String id);

	List<Workspace> findByCourseId(String id);

	List<Workspace> findByCourseTeacherUserId(String userId);

	Workspace findByStudentUserIdAndActive(String userId, boolean active);

}

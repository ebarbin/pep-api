package ar.edu.uade.pfi.pep.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.uade.pfi.pep.repository.document.Workspace;

public interface WorkspaceRepository extends MongoRepository<Workspace, String> {

	void deleteByStudentIdAndCourseId(String studentId, String courseId);

	List<Workspace> findByStudentUserId(String userId);

	List<Workspace> findByProblemsProblemId(String id);

	List<Workspace> findByCourseId(String id);

	List<Workspace> findByCourseTeacherUserId(String userId);

	Workspace findByStudentUserIdAndActive(String userId, boolean active);

}
